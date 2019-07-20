package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class VenueDetails(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("contact")
    val contact: Contact?,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("categories")
    val categories: List<Category>,

    @field:SerializedName("rating")
    val rating: Float,

    @field:SerializedName("photos")
    val photos: Photos
)