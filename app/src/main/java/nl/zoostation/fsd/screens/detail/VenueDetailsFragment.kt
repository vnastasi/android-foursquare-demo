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
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_venue_details.*
import nl.zoostation.fsd.BR
import nl.zoostation.fsd.R
import nl.zoostation.fsd.app.applicationComponent
import nl.zoostation.fsd.app.setUpNavigationEnabled
import nl.zoostation.fsd.databinding.FragmentVenueDetailsBinding
import nl.zoostation.fsd.persistence.model.VenueDetails
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
        setUpNavigationEnabled(true)
    }

    private fun startObserving() {
        viewModel = VenueDetailsViewModel.obtain(requireActivity(), viewModelFactory)
        viewModel.detailsState.observe(viewLifecycleOwner, detailsStateObserver)
        viewModel.onLoadDetails(venueId)
    }

    private fun handleDetailsState(state: VenueDetailsState) {
        when (state) {
            is VenueDetailsState.Loading -> {
                mainContentCard.hide()
                txtMessage.hide()
                progressBar.show()
            }

            is VenueDetailsState.Loaded -> {
                progressBar.hide()
                txtMessage.hide()
                mainContentCard.show()
                handleDataLoaded(state.details)
            }

            is VenueDetailsState.Failed -> {
                progressBar.hide()
                mainContentCard.hide()
                txtMessage.show()
                handleFailure(state.throwable)
            }
        }
    }

    private fun handleDataLoaded(venueDetails: VenueDetails) {
        dataBinding.setVariable(BR.venue, venueDetails.venue)
        dataBinding.executePendingBindings()
        showPhoto(venueDetails)
    }

    private fun handleFailure(throwable: Throwable) {
        txtMessage.setText(R.string.message_failed_details)

        val errorMessage = throwable.message ?: "Unknown error"
        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
        Log.e(TAG, errorMessage, throwable)
    }

    private fun showPhoto(venueDetails: VenueDetails) {
        if (venueDetails.photos.isNotEmpty()) {
            Glide.with(this)
                .load(venueDetails.photos[0].url)
                .placeholder(R.drawable.ic_photo_camera)
                .into(imgVenuePhoto)
        } else {
            imgVenuePhoto.setImageResource(R.drawable.ic_photo_camera)
        }
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