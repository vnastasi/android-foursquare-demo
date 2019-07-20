package nl.zoostation.fsd.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RxModule {

    @Provides
    @Singleton
    @Named("ioScheduler")
    fun provideIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("mainScheduler")
    fun provideMainScheduler():Scheduler = AndroidSchedulers.mainThread()
}