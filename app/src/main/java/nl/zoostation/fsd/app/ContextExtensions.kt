package nl.zoostation.fsd.app

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import nl.zoostation.fsd.di.ApplicationComponent

fun FragmentActivity.requireApplication() =
    requireNotNull(applicationContext as? FoursquareDemoApplication) {
        "Application context must be of type ${FoursquareDemoApplication::class.java}"
    }

fun Fragment.requireApplication() =
    requireNotNull(requireContext().applicationContext as? FoursquareDemoApplication) {
        "Application context must be of type ${FoursquareDemoApplication::class.java}"
    }

val FragmentActivity.applicationComponent: ApplicationComponent
    get() = requireApplication().applicationComponent

val Fragment.applicationComponent: ApplicationComponent
    get() = requireApplication().applicationComponent

fun Fragment.setUpNavigationEnabled(flag: Boolean) {
    val activity = requireActivity()
    if (activity is AppCompatActivity) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(flag)
    }
}
