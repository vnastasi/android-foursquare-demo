package nl.zoostation.fsd.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.persistence.model.Venue

class VenueRepositoryImpl(
    private val venueDao: VenueDAO,
    private val venueApiClient: VenueApiClient
) : VenueRepository {

    override fun searchVenues(place: String): Single<List<Venue>> =
        searchFromDatabase(place).switchIfEmpty(searchFromApi(place)).toList()

    private fun searchFromDatabase(place: String): Observable<Venue> =
        Observable.fromCallable { venueDao.findVenues(place) }
            .flatMapIterable { it }
            .doOnSubscribe { Log.d(LOG_TAG, "Searching from database") }

    private fun searchFromApi(place: String): Observable<Venue> =
        venueApiClient.searchVenues(place)
            .doOnNext { venueDao.insert(it) }
            .doOnSubscribe { Log.d(LOG_TAG, "Searching from Foursquare API") }

    companion object {
        private const val LOG_TAG = "VENUE_REPOSITORY"
    }
}