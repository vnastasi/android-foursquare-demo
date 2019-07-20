package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T>(

    @field:SerializedName("response")
    val response: T
)