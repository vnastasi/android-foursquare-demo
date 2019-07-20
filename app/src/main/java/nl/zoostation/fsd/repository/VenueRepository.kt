package nl.zoostation.fsd.repository

import io.reactivex.Single
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.persistence.model.VenueDetails

interface VenueRepository {

    fun searchVenues(place: String): Single<List<Venue>>

    fun getVenueDetails(id: String): Single<VenueDetails>
}
