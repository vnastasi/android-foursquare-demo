package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class VenueSearchWrapper(

    @field:SerializedName("venues")
    val venues: List<VenueSearchItem>
)