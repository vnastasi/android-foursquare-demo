package nl.zoostation.fsd.api.client.impl

import io.reactivex.Observable
import nl.zoostation.fsd.api.ApiConstants
import nl.zoostation.fsd.api.client.RawVenueApiClient
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.persistence.model.ContactInfo
import nl.zoostation.fsd.persistence.model.Coordinates
import nl.zoostation.fsd.persistence.model.Location
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.api.model.Location as ApiLocation
import nl.zoostation.fsd.api.model.VenueSearchItem as ApiVenueSearchItem

class VenueApiClientImpl(
    private val rawVenueApiClient: RawVenueApiClient
) : VenueApiClient {

    override fun searchVenues(near: String): Observable<Venue> =
        rawVenueApiClient.searchVenues(near, ApiConstants.RADIUS_VALUE, ApiConstants.LIMIT_VALUE)
            .map(ApiResponseMapper())
            .map { it.response.venues }
            .flattenAsObservable { it }
            .map { it.toPersistentVenue() }

    private fun ApiVenueSearchItem.toPersistentVenue(): Venue =
        Venue(
            id = this.id,
            name = this.name,
            category = this.categories.map { it.name }.firstOrNull(),
            location = this.location.toPersistentLocation(),
            contactInfo = ContactInfo()
        )

    private fun ApiLocation.toPersistentLocation(): Location =
        Location(
            address = this.address,
            postalCode = this.postalCode,
            city = this.city,
            state = this.state,
            country = this.country,
            coordinates = Coordinates(this.latitude, this.longitude)
        )
}
