package nl.zoostation.fsd.screens.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_venue_details.*
import nl.zoostation.fsd.BR
import nl.zoostation.fsd.R
import nl.zoostation.fsd.app.applicationComponent
import nl.zoostation.fsd.databinding.FragmentVenueDetailsBinding
import nl.zoostation.fsd.screens.hide
import nl.zoostation.fsd.screens.show
import javax.inject.Inject

class VenueDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: VenueDetailsViewModel.Factory

    private lateinit var dataBinding: FragmentVenueDetailsBinding
    private lateinit var viewModel: VenueDetailsViewModel

    private val detailsStateObserver = Observer<VenueDetailsState> { it?.run { handleDetailsState(this) } }

    private val venueId: String
        get() = requireNotNull(arguments?.getString(KEY_VENUE_ID))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startObserving()
    }

    private fun startObserving() {
        viewModel = VenueDetailsViewModel.obtain(requireActivity(), viewModelFactory)
        viewModel.detailsState.observe(viewLifecycleOwner, detailsStateObserver)
        viewModel.onLoadDetails(venueId)
    }

    private fun handleDetailsState(state: VenueDetailsState) {
        when (state) {
            is VenueDetailsState.Loading ->
                showProgressBar()

            is VenueDetailsState.Loaded -> {
                hideProgressBar()
                dataBinding.setVariable(BR.venue, state.details.venue)
                dataBinding.executePendingBindings()
            }

            is VenueDetailsState.Failed -> {
                hideProgressBar()
                val message = state.throwable.message ?: "Unknown error"
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                Log.e("VenueListFragment", message, state.throwable)
            }
        }
    }

    private fun showProgressBar() {
        mainContentGroup.hide()
        progressBar.show()
    }

    private fun hideProgressBar() {
        progressBar.hide()
        mainContentGroup.show()
    }

    companion object {
        private const val KEY_VENUE_ID = "VENUE_ID"

        const val TAG = "VENUE_DETAILS"

        fun create(venueId: String): VenueDetailsFragment {
            val arguments = bundleOf(KEY_VENUE_ID to venueId)
            return VenueDetailsFragment().apply { this.arguments = arguments }
        }
    }
}