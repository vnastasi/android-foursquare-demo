package nl.zoostation.fsd.screens.list

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import nl.zoostation.fsd.app.mutate
import nl.zoostation.fsd.repository.VenueRepository

class VenueListViewModel(
    private val venueRepository: VenueRepository,
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler
) : ViewModel() {

    private val disposables = CompositeDisposable()

    val listState: LiveData<VenueListState> = MutableLiveData()

    var lastSearchedPlace: String = ""
        private set

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onSearchVenues(place: String) {
        lastSearchedPlace = place
        venueRepository.searchVenues(place)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnSubscribe { listState.mutate().value = VenueListState.Loading }
            .subscribeBy(
                onSuccess = { listState.mutate().value = VenueListState.Loaded(it) },
                onError = { listState.mutate().value = VenueListState.Failed(it) }
            )
            .addTo(disposables)
    }

    class Factory(
        private val venueRepository: VenueRepository,
        private val ioScheduler: Scheduler,
        private val mainScheduler: Scheduler
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            VenueListViewModel(venueRepository, ioScheduler, mainScheduler) as T
    }

    companion object {

        fun obtain(scope: FragmentActivity, factory: Factory): VenueListViewModel =
            ViewModelProviders.of(scope, factory)[VenueListViewModel::class.java]
    }
}
