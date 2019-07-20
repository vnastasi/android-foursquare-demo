package nl.zoostation.fsd.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import nl.zoostation.fsd.persistence.FoursquareDemoDatabase
import nl.zoostation.fsd.persistence.dao.PhotoDAO
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application):FoursquareDemoDatabase =
        Room.databaseBuilder(application, FoursquareDemoDatabase::class.java, "fsd").build()

    @Provides
    @Singleton
    fun provideVenueDao(database: FoursquareDemoDatabase): VenueDAO =
        database.getVenueDAO()

    @Provides
    @Singleton
    fun providePlaceDAO(database: FoursquareDemoDatabase): PlaceDAO =
        database.getPlaceDAO()

    @Provides
    @Singleton
    fun providePhotoDAO(database: FoursquareDemoDatabase): PhotoDAO =
        database.getPhotoDAO()
}