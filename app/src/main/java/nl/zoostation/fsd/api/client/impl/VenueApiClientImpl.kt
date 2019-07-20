package nl.zoostation.fsd.api.client.impl

import io.reactivex.Observable
import nl.zoostation.fsd.api.ApiConstants
import nl.zoostation.fsd.api.client.RawVenueApiClient
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.persistence.model.Venue

class VenueApiClientImpl(
    private val rawVenueApiClient: RawVenueApiClient
) : VenueApiClient {

    override fun searchVenues(near: String): Observable<Venue> =
        rawVenueApiClient.searchVenues(near, ApiConstants.RADIUS_VALUE, ApiConstants.LIMIT_VALUE)
            .map(ApiResponseMapper())
            .flattenAsObservable { it }
}
