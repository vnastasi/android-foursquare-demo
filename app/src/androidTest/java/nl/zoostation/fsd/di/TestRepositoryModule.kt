package nl.zoostation.fsd.di

import com.nhaarman.mockitokotlin2.mock
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.persistence.dao.PhotoDAO
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.repository.VenueRepository
import org.mockito.Mockito

class TestRepositoryModule : RepositoryModule() {

    override fun provideVenueRepository(
        venueDAO: VenueDAO,
        placeDAO: PlaceDAO,
        photoDAO: PhotoDAO,
        venueApiClient: VenueApiClient
    ): VenueRepository = mockVenueRepository

    companion object {
        val mockVenueRepository = mock<VenueRepository>(defaultAnswer = Mockito.RETURNS_DEEP_STUBS)
    }
}