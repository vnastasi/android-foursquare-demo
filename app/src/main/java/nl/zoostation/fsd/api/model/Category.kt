package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class Category(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String
)