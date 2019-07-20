package nl.zoostation.fsd.screens.detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import nl.zoostation.fsd.repository.VenueRepository

class VenueDetailsViewModel(
    private val venueRepository: VenueRepository,
    private val ioScheduler: Scheduler,
    private val mainScheduler: Scheduler
): ViewModel() {

    private val disposables = CompositeDisposable()

    val detailsState: LiveData<VenueDetailsState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onLoadDetails(venueId: String) {
        venueRepository.getVenueDetails(venueId)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnSubscribe { detailsState.mutate().value = VenueDetailsState.Loading }
            .subscribeBy(
                onSuccess = { detailsState.mutate().value = VenueDetailsState.Loaded(it) },
                onError = { detailsState.mutate().value = VenueDetailsState.Failed(it) }
            )
            .addTo(disposables)
    }

    private fun <T> LiveData<T>.mutate() = this as MutableLiveData<T>

    class Factory(
        private val venueRepository: VenueRepository,
        private val ioScheduler: Scheduler,
        private val mainScheduler: Scheduler
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            VenueDetailsViewModel(venueRepository, ioScheduler, mainScheduler) as T

    }

    companion object {

        fun obtain(scope: FragmentActivity, factory: Factory): VenueDetailsViewModel =
            ViewModelProviders.of(scope, factory)[VenueDetailsViewModel::class.java]
    }
}