package nl.zoostation.fsd.persistence.dao

import androidx.room.*
import nl.zoostation.fsd.persistence.model.Venue

@Dao
interface VenueDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venue: Venue)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(venue: Venue)

    @Query("SELECT v.* FROM venues v INNER JOIN places p ON v.venue_id = p.venue_id WHERE p.name LIKE '%'||:place||'%'")
    fun findVenues(place: String): List<Venue>

    @Query("SELECT * FROM venues WHERE venue_id = :id AND incomplete = 0")
    fun getVenueDetails(id: String): Venue?
}
