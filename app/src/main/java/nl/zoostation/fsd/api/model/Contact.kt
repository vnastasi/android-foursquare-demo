package nl.zoostation.fsd.api.model

import com.google.gson.annotations.SerializedName

data class Contact(

    @field:SerializedName("phone")
    val phone: String?,

    @field:SerializedName("twitter")
    val twitterHandle: String?,

    @field:SerializedName("instagram")
    val instagramHandle: String?,

    @field:SerializedName("facebookUsername")
    val facebookHandle: String?
)