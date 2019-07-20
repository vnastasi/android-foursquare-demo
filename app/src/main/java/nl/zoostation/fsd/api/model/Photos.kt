package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class Photos(

    @field:SerializedName("groups")
    val groups: List<PhotoGroup>
)