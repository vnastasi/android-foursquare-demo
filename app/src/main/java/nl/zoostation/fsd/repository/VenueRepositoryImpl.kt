package nl.zoostation.fsd.repository

import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import nl.zoostation.fsd.api.client.VenueApiClient
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
        searchFromDatabase(place).switchIfEmpty(searchFromApi(place)).toList()

    override fun getVenueDetails(id: String): Single<VenueDetails> =
        getDetailsFromDatabase(id).switchIfEmpty(getDetailsFromApi(id)).singleOrError()

    private fun searchFromDatabase(place: String): Observable<Venue> =
        Observable.fromCallable { venueDAO.findVenues(place) }
            .flatMapIterable { it }
            .doOnSubscribe { Log.d(LOG_TAG, "Searching from database") }

    private fun searchFromApi(place: String): Observable<Venue> =
        venueApiClient.searchVenues(place)
            .doOnNext { venue ->
                venueDAO.insert(venue)
                placeDAO.insert(Place(venueId = venue.id, name = place))
            }
            .doOnSubscribe { Log.d(LOG_TAG, "Searching from Foursquare API") }

    private fun getDetailsFromDatabase(id: String): Observable<VenueDetails> =
        Maybe.fromCallable { venueDAO.getVenueDetails(id) }
            .zipWith(
                Maybe.fromCallable { photoDAO.getVenuePhotos(id) },
                BiFunction { venue: Venue?, photos: List<Photo> ->
                    VenueDetails(requireNotNull(venue), photos)
                }
            )
            .toObservable()
            .doOnSubscribe { Log.d(LOG_TAG, "Getting details from database") }

    private fun getDetailsFromApi(id: String): Observable<VenueDetails> =
        venueApiClient.getVenueDetails(id)
            .doOnNext { venueDetails ->
                venueDAO.update(venueDetails.venue.copy(incomplete = false))
                photoDAO.insert(venueDetails.photos)
            }
            .doOnSubscribe { Log.d(LOG_TAG, "Getting details from Foursquare API") }

    companion object {
        private const val LOG_TAG = "VENUE_REPOSITORY"
    }
}