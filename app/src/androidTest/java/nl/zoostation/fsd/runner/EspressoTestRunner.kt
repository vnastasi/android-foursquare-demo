package nl.zoostation.fsd.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import nl.zoostation.fsd.app.TestFoursquareDemoApplication

class EspressoTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
        super.newApplication(cl, TestFoursquareDemoApplication::class.java.name, context)
}