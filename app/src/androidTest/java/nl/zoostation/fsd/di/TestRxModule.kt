package nl.zoostation.fsd.di

import android.os.AsyncTask
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestRxModule : RxModule() {

    override fun provideIOScheduler(): Scheduler = Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR)
}