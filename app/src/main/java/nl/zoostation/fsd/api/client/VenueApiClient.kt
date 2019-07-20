package nl.zoostation.fsd.api.client

import io.reactivex.Observable
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.persistence.model.VenueDetails

interface VenueApiClient {

    fun searchVenues(near: String): Observable<Venue>

    fun getVenueDetails(venueId: String): Observable<VenueDetails>
}
