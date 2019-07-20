package nl.zoostation.fsd.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.transaction
import kotlinx.android.synthetic.main.activity_main.*
import nl.zoostation.fsd.R
import nl.zoostation.fsd.app.applicationComponent
import nl.zoostation.fsd.screens.list.VenueListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent.inject(this)
        setContentView(R.layout.activity_main)
        setupActionBar()
        showVenueList()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun showVenueList() {
        supportFragmentManager.transaction {
            replace(R.id.fragmentContainer, VenueListFragment(), VenueListFragment.TAG)
        }
    }
}
