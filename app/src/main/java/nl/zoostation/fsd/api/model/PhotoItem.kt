package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class PhotoItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("prefix")
    val prefix: String,

    @field:SerializedName("suffix")
    val suffix: String
)