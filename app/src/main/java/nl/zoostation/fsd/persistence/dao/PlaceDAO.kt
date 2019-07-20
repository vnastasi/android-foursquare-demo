package nl.zoostation.fsd.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import nl.zoostation.fsd.persistence.model.Place

@Dao
interface PlaceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)
}