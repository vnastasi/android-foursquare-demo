package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo

data class ContactInfo(

    @ColumnInfo(name = "phone")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "twitter")
    val twitterHandle: String? = null,

    @ColumnInfo(name = "instagram")
    val instagramHandle: String? = null,

    @ColumnInfo(name = "facebook")
    val facebookHandle: String? = null
)