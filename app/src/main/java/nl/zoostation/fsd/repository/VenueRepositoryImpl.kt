package nl.zoostation.fsd.repository

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.exception.NetworkUnavailableException
import nl.zoostation.fsd.persistence.dao.PhotoDAO
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.persistence.model.Photo
import nl.zoostation.fsd.persistence.model.Place
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.persistence.model.VenueDetails

class VenueRepositoryImpl(
    private val venueDAO: VenueDAO,
    private val placeDAO: PlaceDAO,
    private val photoDAO: PhotoDAO,
    private val venueApiClient: VenueApiClient
) : VenueRepository {

    override fun searchVenues(place: String): Single<List<Venue>> =
        searchFromApi(place)
            .onErrorResumeNext(Function<Throwable, ObservableSource<Venue>> { throwable ->
                if (throwable is NetworkUnavailableException) {
                    searchFromDatabase(place)
                } else {
                    Observable.error(throwable)
                }
            })
            .toList()

    override fun getVenueDetails(id: String): Single<VenueDetails> =
        getDetailsFromApi(id)
            .onErrorResumeNext(Function<Throwable, ObservableSource<VenueDetails>> { throwable ->
                if (throwable is NetworkUnavailableException) {
                    getDetailsFromDatabase(id)
                } else {
                    Observable.error(throwable)
                }
            })
            .singleOrError()

    private fun searchFromDatabase(place: String): Observable<Venue> =
        Observable.fromCallable { venueDAO.findVenues(place) }
            .flatMapIterable { it }

    private fun searchFromApi(place: String): Observable<Venue> =
        venueApiClient.searchVenues(place)
            .doOnNext { venue ->
                venueDAO.insert(venue)
                placeDAO.insert(Place(venueId = venue.id, name = place))
            }

    private fun getDetailsFromDatabase(id: String): Observable<VenueDetails> =
        Maybe.fromCallable { venueDAO.getVenueDetails(id) }
            .zipWith(
                Maybe.fromCallable { photoDAO.getVenuePhotos(id) },
                BiFunction { venue: Venue?, photos: List<Photo> ->
                    VenueDetails(requireNotNull(venue), photos)
                }
            )
            .toObservable()

    private fun getDetailsFromApi(id: String): Observable<VenueDetails> =
        venueApiClient.getVenueDetails(id)
            .doOnNext { venueDetails ->
                venueDAO.update(venueDetails.venue.copy(incomplete = false))
                photoDAO.insert(venueDetails.photos)
            }
}