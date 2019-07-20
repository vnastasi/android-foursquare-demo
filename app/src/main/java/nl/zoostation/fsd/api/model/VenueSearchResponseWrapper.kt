package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class VenueSearchResponseWrapper(

    @field:SerializedName("venues")
    val venues: List<VenueSearchItem>
)