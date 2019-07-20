package nl.zoostation.fsd.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.persistence.model.VenueDetails

@Dao
interface VenueDao {

    @Insert
    fun insert(venue: Venue)

    @Update
    fun update(venue: Venue)

    @Query("SELECT * FROM venues WHERE city LIKE '%'||:place||'%' LIMIT 10")
    fun findVenues(place: String): List<Venue>

    @Query("SELECT * FROM venues WHERE venue_id = :id")
    fun getVenueDetails(id: String): VenueDetails
}
