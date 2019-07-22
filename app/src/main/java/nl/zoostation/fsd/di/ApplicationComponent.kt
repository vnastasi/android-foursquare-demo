package nl.zoostation.fsd.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nl.zoostation.fsd.screens.MainActivity
import nl.zoostation.fsd.screens.detail.VenueDetailsFragment
import nl.zoostation.fsd.screens.list.VenueListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PersistenceModule::class, RepositoryModule::class, RxModule::class, ViewModelFactoryModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(venueListFragment: VenueListFragment)

    fun inject(venueDetailsFragment: VenueDetailsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun rxModule(rxModule: RxModule): Builder

        fun repositoryModel(repositoryModule: RepositoryModule): Builder

        fun build(): ApplicationComponent
    }
}