package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class VenueSearchItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("categories")
    val categories: List<Category>,

    @field:SerializedName("location")
    val location: Location
)
