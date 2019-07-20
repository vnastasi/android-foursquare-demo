package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class VenueDetailsWrapper(

    @field:SerializedName("venue")
    val venue: VenueDetails
)