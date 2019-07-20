package nl.zoostation.fsd.screens.list

import nl.zoostation.fsd.persistence.model.Venue

sealed class VenueListState {

    object Loading : VenueListState()

    data class Loaded(val list: List<Venue>) : VenueListState()

    data class Failed(val throwable: Throwable) : VenueListState()
}