package nl.zoostation.fsd.api.client

import io.reactivex.Observable
import nl.zoostation.fsd.persistence.model.Venue

interface VenueApiClient {

    fun searchVenues(near: String): Observable<Venue>
}
