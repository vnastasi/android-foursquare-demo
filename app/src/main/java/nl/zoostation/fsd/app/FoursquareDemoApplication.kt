package nl.zoostation.fsd.app

import android.app.Application
import nl.zoostation.fsd.di.ApplicationComponent
import nl.zoostation.fsd.di.DaggerApplicationComponent

class FoursquareDemoApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
        initComponent()
    }

    private fun initComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
}
