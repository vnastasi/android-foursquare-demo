package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class PhotoGroup(

    @field:SerializedName("items")
    val items: List<PhotoItem>
)