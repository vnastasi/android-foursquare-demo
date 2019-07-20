package nl.zoostation.fsd.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.persistence.model.Photo
import nl.zoostation.fsd.persistence.model.Place
import nl.zoostation.fsd.persistence.model.Venue

@Database(entities = [Photo::class, Venue::class, Place::class], version = 1)
abstract class FoursquareDemoDatabase : RoomDatabase() {

    abstract fun getVenueDAO(): VenueDAO

    abstract fun getPlaceDAO(): PlaceDAO
}