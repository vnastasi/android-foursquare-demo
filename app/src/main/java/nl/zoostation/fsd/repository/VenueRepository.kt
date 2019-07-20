package nl.zoostation.fsd.repository

import io.reactivex.Single
import nl.zoostation.fsd.persistence.model.Venue

interface VenueRepository {

    fun searchVenues(place: String): Single<List<Venue>>
}
