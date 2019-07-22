package nl.zoostation.fsd.app

import nl.zoostation.fsd.di.ApplicationComponent
import nl.zoostation.fsd.di.DaggerApplicationComponent
import nl.zoostation.fsd.di.TestRepositoryModule
import nl.zoostation.fsd.di.TestRxModule

class TestFoursquareDemoApplication : FoursquareDemoApplication() {

    override fun initComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .application(this)
            .rxModule(TestRxModule())
            .repositoryModel(TestRepositoryModule())
            .build()
}