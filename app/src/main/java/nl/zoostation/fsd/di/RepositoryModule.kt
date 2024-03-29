package nl.zoostation.fsd.di

import dagger.Module
import dagger.Provides
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.app.OpenForTesting
import nl.zoostation.fsd.persistence.dao.PhotoDAO
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.repository.VenueRepository
import nl.zoostation.fsd.repository.VenueRepositoryImpl
import javax.inject.Singleton

@OpenForTesting
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideVenueRepository(
        venueDAO: VenueDAO,
        placeDAO: PlaceDAO,
        photoDAO: PhotoDAO,
        venueApiClient: VenueApiClient
    ): VenueRepository =
        VenueRepositoryImpl(venueDAO, placeDAO, photoDAO, venueApiClient)
}