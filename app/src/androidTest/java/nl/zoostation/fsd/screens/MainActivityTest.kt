package nl.zoostation.fsd.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import nl.zoostation.fsd.di.TestRepositoryModule
import nl.zoostation.fsd.persistence.model.VenueDetails
import nl.zoostation.fsd.util.TestDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @After
    fun tearDown() {
        reset(TestRepositoryModule.mockVenueRepository)
    }

    @Test
    fun testVenueList() {
        val venue = TestDataFactory.createIncompleteVenue()
        whenever(TestRepositoryModule.mockVenueRepository.searchVenues(eq(PLACE)))
            .thenReturn(Single.just(listOf(venue)))

        activityRule.launchActivity(null)

        toolbar {
            selectSearch()
            submitSearchQuery(PLACE)
        }

        venueList {
            checkName(0, venue.name)
            checkAddress(0, venue.location.formatAddress())
        }
    }

    @Test
    fun testVenueDetails() {
        val venue = TestDataFactory.createCompleteVenue()
        val photo = TestDataFactory.createPhoto()
        whenever(TestRepositoryModule.mockVenueRepository.searchVenues(eq(PLACE)))
            .thenReturn(Single.just(listOf(venue)))
        whenever(TestRepositoryModule.mockVenueRepository.getVenueDetails(venue.id))
            .thenReturn(Single.just(VenueDetails(venue, listOf(photo))))

        activityRule.launchActivity(null)

        toolbar {
            selectSearch()
            submitSearchQuery(PLACE)
        }
        venueList {
            click(0)
        }

        venueDetails {
            checkName(venue.name)
            checkRating(requireNotNull(venue.rating))
            checkDescription(requireNotNull(venue.description))
            checkAddress(venue.location.formatAddress())
            checkPhoneNumber(requireNotNull(venue.contactInfo?.phoneNumber))
        }
    }

    private companion object {
        const val PLACE = "place"
    }
}