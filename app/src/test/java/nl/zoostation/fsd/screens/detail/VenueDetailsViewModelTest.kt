package nl.zoostation.fsd.screens.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import nl.zoostation.fsd.repository.VenueRepository
import nl.zoostation.fsd.util.ImmediateScheduler
import nl.zoostation.fsd.util.RecordingObserver
import nl.zoostation.fsd.util.TestDataFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class VenueDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockVenueRepository = mock<VenueRepository>()
    private val detailsStateObserver = RecordingObserver<VenueDetailsState>()

    private val viewModel = VenueDetailsViewModel(
        venueRepository = mockVenueRepository,
        ioScheduler = ImmediateScheduler.create(),
        mainScheduler = ImmediateScheduler.create()
    )

    @Before
    fun setUp() {
        viewModel.detailsState.observeForever(detailsStateObserver)
    }

    @Test
    fun `when loading details is successful then expect details`() {
        val venueDetails = TestDataFactory.createVenueDetails()
        val id = venueDetails.venue.id
        whenever(mockVenueRepository.getVenueDetails(eq(id))).thenReturn(Single.just(venueDetails))

        viewModel.onLoadDetails(id)

        detailsStateObserver.assertSequence(
            VenueDetailsState.Loading,
            VenueDetailsState.Loaded(venueDetails)
        )
    }

    @Test
    fun `when loading details fails then expect exception`() {
        val id = UUID.randomUUID().toString()
        val exception = RuntimeException()
        whenever(mockVenueRepository.getVenueDetails(eq(id))).thenReturn(Single.error(exception))

        viewModel.onLoadDetails(id)

        detailsStateObserver.assertSequence(
            VenueDetailsState.Loading,
            VenueDetailsState.Failed(exception)
        )
    }
}