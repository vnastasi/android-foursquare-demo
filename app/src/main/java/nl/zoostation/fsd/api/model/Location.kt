package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class Location(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("postalCode")
    val postalCode: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("state")
    val state: String,

    @field:SerializedName("country")
    val country: String,

    @field:SerializedName("lat")
    val latitude: Double,

    @field:SerializedName("lng")
    val longitude: Double
)