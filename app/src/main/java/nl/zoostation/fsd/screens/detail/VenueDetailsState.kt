package nl.zoostation.fsd.screens.detail

import nl.zoostation.fsd.persistence.model.VenueDetails

sealed class VenueDetailsState {

    object Loading : VenueDetailsState()

    data class Loaded(val details: VenueDetails) : VenueDetailsState()

    data class Failed(val throwable: Throwable) : VenueDetailsState()
}
