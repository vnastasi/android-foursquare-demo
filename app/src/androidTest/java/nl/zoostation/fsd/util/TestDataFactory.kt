package nl.zoostation.fsd.util

import nl.zoostation.fsd.persistence.model.*
import java.util.*

object TestDataFactory {

    fun createIncompleteVenue(): Venue =
        Venue(
            id = UUID.randomUUID().toString(),
            name = "An awesome venue",
            incomplete = true,
            location = createLocation()
        )

    fun createCompleteVenue(): Venue =
        Venue(
            id = UUID.randomUUID().toString(),
            name = "An awesome venue",
            incomplete = false,
            location = createLocation(),
            contactInfo = createContactInfo(),
            rating = 4.5f,
            description = "A very very long description",
            category = "A category"
        )

    fun createLocation(): Location =
        Location(
            address = "address",
            city = "city",
            postalCode = "postalCode",
            state = "state",
            country = "country",
            coordinates = Coordinates(0.0, 0.0)
        )

    fun createContactInfo(): ContactInfo =
        ContactInfo(
            phoneNumber = "+3123456789",
            facebookHandle = "facebook_user"
        )

    fun createPhoto(): Photo =
        Photo(
            id = UUID.randomUUID().toString(),
            url = "http://www.google.com",
            venueId = UUID.randomUUID().toString()
        )

    fun createVenueDetails(): VenueDetails =
        VenueDetails(createCompleteVenue(), listOf(createPhoto()))
}