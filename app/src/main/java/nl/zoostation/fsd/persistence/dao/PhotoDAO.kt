package nl.zoostation.fsd.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.zoostation.fsd.persistence.model.Photo

@Dao
interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photos: List<Photo>)

    @Query("SELECT * FROM photos WHERE venue_id = :venueId")
    fun getVenuePhotos(venueId: String): List<Photo>
}