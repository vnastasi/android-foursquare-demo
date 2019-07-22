package nl.zoostation.fsd.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object ImmediateScheduler {

    fun create(): Scheduler = Schedulers.from { it.run() }
}