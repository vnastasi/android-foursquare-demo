package nl.zoostation.fsd.screens.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_venue_list.*
import nl.zoostation.fsd.R
import nl.zoostation.fsd.app.applicationComponent
import javax.inject.Inject

class VenueListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: VenueListViewModel.Factory

    private lateinit var viewModel: VenueListViewModel

    private val listStateObserver = Observer<VenueListState> { it?.run { handleListStateChange(this) } }

    private val listAdapter = VenueListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        layoutInflater.inflate(R.layout.fragment_venue_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserving()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.venue_search_menu, menu)
        val menuItem = requireNotNull(menu.findItem(R.id.app_bar_search))
        setupSearch(menuItem)
        setupList()
    }

    private fun startObserving() {
        viewModel = VenueListViewModel.obtain(requireActivity(), viewModelFactory)
        viewModel.listState.observe(viewLifecycleOwner, listStateObserver)
    }

    private fun setupSearch(menuItem: MenuItem) {
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.lastSearchedPlace
                viewModel.onSearchVenues(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun setupList() {
        recyclerView.adapter = listAdapter
        swipeRefresh.setOnRefreshListener { viewModel.onSearchVenues(viewModel.lastSearchedPlace) }
    }

    private fun handleListStateChange(state: VenueListState) {
        when (state) {
            is VenueListState.Loading -> {
                swipeRefresh.isRefreshing = true
            }
            is VenueListState.Loaded -> {
                swipeRefresh.isRefreshing = false
                listAdapter.submitList(state.list)
            }
            is VenueListState.Failed -> {
                swipeRefresh.isRefreshing = false
                val message = state.throwable.message ?: "Unknown error"
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                Log.e("VenueListFragment", message, state.throwable)
            }
        }
    }

    companion object {
        const val TAG = "VENUE_LIST"
    }
}