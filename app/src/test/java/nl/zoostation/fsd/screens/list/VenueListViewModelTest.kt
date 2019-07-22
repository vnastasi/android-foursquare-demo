package nl.zoostation.fsd.screens.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.repository.VenueRepository
import nl.zoostation.fsd.util.ImmediateScheduler
import nl.zoostation.fsd.util.RecordingObserver
import nl.zoostation.fsd.util.TestDataFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VenueListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockVenueRepository = mock<VenueRepository>()
    private val venueListStateObserver = RecordingObserver<VenueListState>()
    private val selectedVenueObserver = RecordingObserver<Venue>()

    private val viewModel = VenueListViewModel(
        venueRepository = mockVenueRepository,
        ioScheduler = ImmediateScheduler.create(),
        mainScheduler = ImmediateScheduler.create()
    )

    @Before
    fun setUp() {
        viewModel.listState.observeForever(venueListStateObserver)
        viewModel.selectedVenue.observeForever(selectedVenueObserver)
    }

    @Test
    fun `when search is successful then expect a list of venues`() {
        val venue = TestDataFactory.createIncompleteVenue()
        whenever(mockVenueRepository.searchVenues(eq(PLACE))).thenReturn(Single.just(listOf(venue)))

        viewModel.onSearchVenues(PLACE)

        assertThat(viewModel.lastSearchedPlace).isEqualTo(PLACE)
        venueListStateObserver.assertSequence(
            VenueListState.Loading,
            VenueListState.Loaded(listOf(venue))
        )
    }

    @Test
    fun `when search fails then expect an exception`() {
        val exception = RuntimeException()
        whenever(mockVenueRepository.searchVenues(eq(PLACE))).thenReturn(Single.error(exception))

        viewModel.onSearchVenues(PLACE)

        assertThat(viewModel.lastSearchedPlace).isEqualTo(PLACE)
        venueListStateObserver.assertSequence(
            VenueListState.Loading,
            VenueListState.Failed(exception)
        )
    }

    @Test
    fun `when venue selected then expect value to be recorded`() {
        val venue = TestDataFactory.createIncompleteVenue()

        viewModel.onVenueSelected(venue)

        selectedVenueObserver.assertSequence(venue)
    }

    private companion object {
        const val PLACE = "An awesome place"
    }
}