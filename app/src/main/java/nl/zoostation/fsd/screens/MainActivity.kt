package nl.zoostation.fsd.screens

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import nl.zoostation.fsd.R
import nl.zoostation.fsd.app.applicationComponent
import nl.zoostation.fsd.persistence.model.Venue
import nl.zoostation.fsd.screens.detail.VenueDetailsFragment
import nl.zoostation.fsd.screens.list.VenueListFragment
import nl.zoostation.fsd.screens.list.VenueListViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: VenueListViewModel.Factory

    private lateinit var viewModel: VenueListViewModel

    private val selectedVenueObserver = Observer<Venue> { it?.run { handleSelectedVenue(this) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent.inject(this)
        setContentView(R.layout.activity_main)
        setupActionBar()
        startObserving()
        showVenueList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun startObserving() {
        viewModel = VenueListViewModel.obtain(this, viewModelFactory)
        viewModel.selectedVenue.observe(this, selectedVenueObserver)
    }

    private fun handleSelectedVenue(venue: Venue) {
        showVenueDetails(venue.id)
    }

    private fun showVenueList() {
        supportFragmentManager.transaction {
            replace(R.id.fragmentContainer, VenueListFragment(), VenueListFragment.TAG)
        }
    }

    private fun showVenueDetails(venueId: String) {
        val fragment = VenueDetailsFragment.create(venueId)
        supportFragmentManager.transaction {
            replace(R.id.fragmentContainer, fragment, VenueDetailsFragment.TAG)
            addToBackStack(VenueDetailsFragment.TAG)
        }
    }
}
