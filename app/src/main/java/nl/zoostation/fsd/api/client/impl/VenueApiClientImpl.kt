package nl.zoostation.fsd.api.client.impl

import io.reactivex.Observable
import nl.zoostation.fsd.api.ApiConstants
import nl.zoostation.fsd.api.client.RawVenueApiClient
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.persistence.model.*
import nl.zoostation.fsd.api.model.Contact as ApiContact
import nl.zoostation.fsd.api.model.Location as ApiLocation
import nl.zoostation.fsd.api.model.Photos as ApiPhotos
import nl.zoostation.fsd.api.model.VenueDetails as ApiVenueDetails
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

    override fun getVenueDetails(venueId: String): Observable<VenueDetails> =
        rawVenueApiClient.getDetails(venueId)
            .toObservable()
            .map(ApiResponseMapper())
            .map { it.response.venue }
            .map { venueDetails ->
                VenueDetails(
                    venue = venueDetails.toPersistentVenue(),
                    photos = venueDetails.photos.toPersistentPhotoList(venueDetails.id)
                )
            }


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

    private fun ApiVenueDetails.toPersistentVenue(): Venue =
        Venue(
            id = this.id,
            name = this.name,
            category = this.categories.map { it.name }.firstOrNull(),
            description = this.description,
            rating = this.rating,
            location = this.location.toPersistentLocation(),
            contactInfo = this.contact?.toPersistentContactInfo()
        )

    private fun ApiContact.toPersistentContactInfo(): ContactInfo =
        ContactInfo(
            phoneNumber = this.phone,
            twitterHandle = this.twitterHandle,
            instagramHandle = this.instagramHandle,
            facebookHandle = this.facebookHandle
        )

    private fun ApiPhotos.toPersistentPhotoList(venueId: String): List<Photo> =
        this.groups
            .flatMap { it.items }
            .map { Photo(id = it.id, url = "${it.prefix}original${it.suffix}", venueId = venueId) }
}
