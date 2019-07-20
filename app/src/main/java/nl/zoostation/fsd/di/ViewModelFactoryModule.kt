package nl.zoostation.fsd.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import nl.zoostation.fsd.repository.VenueRepository
import nl.zoostation.fsd.screens.detail.VenueDetailsViewModel
import nl.zoostation.fsd.screens.list.VenueListViewModel
import javax.inject.Named
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideVenueListViewModelFactory(
        venueRepository: VenueRepository,
        @Named("ioScheduler") ioScheduler: Scheduler,
        @Named("mainScheduler") mainScheduler: Scheduler
    ): VenueListViewModel.Factory =
        VenueListViewModel.Factory(venueRepository, ioScheduler, mainScheduler)

    @Provides
    @Singleton
    fun provideVenueDetailsViewModelFactory(
        venueRepository: VenueRepository,
        @Named("ioScheduler") ioScheduler: Scheduler,
        @Named("mainScheduler") mainScheduler: Scheduler
    ): VenueDetailsViewModel.Factory =
        VenueDetailsViewModel.Factory(venueRepository, ioScheduler, mainScheduler)
}
