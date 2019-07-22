package nl.zoostation.fsd.repository

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import nl.zoostation.fsd.api.client.VenueApiClient
import nl.zoostation.fsd.exception.NetworkUnavailableException
import nl.zoostation.fsd.persistence.dao.PhotoDAO
import nl.zoostation.fsd.persistence.dao.PlaceDAO
import nl.zoostation.fsd.persistence.dao.VenueDAO
import nl.zoostation.fsd.persistence.model.Photo
import nl.zoostation.fsd.persistence.model.Place
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.persistence.model.VenueDetails
import nl.zoostation.fsd.util.TestDataFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class VenueRepositoryTest {

    private val mockVenueDAO = mock<VenueDAO>()
    private val mockPlaceDAO = mock<PlaceDAO>()
    private val mockPhotoDAO = mock<PhotoDAO>()
    private val mockVenueApiClient = mock<VenueApiClient>()

    private val repository = VenueRepositoryImpl(mockVenueDAO, mockPlaceDAO, mockPhotoDAO, mockVenueApiClient)

    @Test
    fun `when network available then expect search from API and data caching`() {
        val venue = TestDataFactory.createIncompleteVenue()
        whenever(mockVenueApiClient.searchVenues(eq(PLACE))).thenReturn(Observable.just(venue))

        repository.searchVenues(PLACE)
            .test()
            .assertValue(listOf(venue))

        val cachedVenueCaptor = argumentCaptor<Venue>()
        val cachedPlaceCaptor = argumentCaptor<Place>()
        verify(mockVenueDAO).insert(cachedVenueCaptor.capture())
        verify(mockPlaceDAO).insert(cachedPlaceCaptor.capture())

        assertThat(cachedVenueCaptor.firstValue).isEqualTo(venue)
        assertThat(cachedPlaceCaptor.firstValue).isEqualTo(Place(PLACE, venue.id))
    }

    @Test
    fun `when network unavailable then expect search from database`() {
        val venue = TestDataFactory.createIncompleteVenue()
        whenever(mockVenueApiClient.searchVenues(eq(PLACE))).thenReturn(Observable.error(NetworkUnavailableException()))
        whenever(mockVenueDAO.findVenues(eq(PLACE))).thenReturn(listOf(venue))

        repository.searchVenues(PLACE)
            .test()
            .assertValue(listOf(venue))

        verify(mockVenueDAO, never()).insert(any())
        verify(mockPlaceDAO, never()).insert(any())
    }

    @Test
    fun `when network available the expect details from API and data caching`() {
        val venue = TestDataFactory.createCompleteVenue().copy(incomplete = true)
        val photo = TestDataFactory.createPhoto()
        val venueDetails = VenueDetails(venue, listOf(photo))
        val id = venue.id

        whenever(mockVenueApiClient.getVenueDetails(eq(id))).thenReturn(Observable.just(venueDetails))

        repository.getVenueDetails(id)
            .test()
            .assertValue(venueDetails)

        val cachedVenueCaptor = argumentCaptor<Venue>()
        val cachedPhotoCaptor = argumentCaptor<List<Photo>>()
        verify(mockVenueDAO).update(cachedVenueCaptor.capture())
        verify(mockPhotoDAO).insert(cachedPhotoCaptor.capture())

        assertThat(cachedVenueCaptor.firstValue).isEqualTo(venue.copy(incomplete = false))
        assertThat(cachedPhotoCaptor.firstValue).containsExactly(photo)
    }

    @Test
    fun `when network unavailable then expect details from database`() {
        val venue = TestDataFactory.createCompleteVenue().copy(incomplete = true)
        val photo = TestDataFactory.createPhoto()
        val venueDetails = VenueDetails(venue, listOf(photo))
        val id = venue.id

        whenever(mockVenueApiClient.getVenueDetails(eq(id))).thenReturn(Observable.error(NetworkUnavailableException()))
        whenever(mockVenueDAO.getVenueDetails(eq(id))).thenReturn(venue)
        whenever(mockPhotoDAO.getVenuePhotos(eq(id))).thenReturn(listOf(photo))

        repository.getVenueDetails(id)
            .test()
            .assertValue(venueDetails)

        verify(mockVenueDAO, never()).update(any())
        verify(mockPhotoDAO, never()).insert(any())
    }

    private companion object {
        const val PLACE = "An awesome place"
    }
}