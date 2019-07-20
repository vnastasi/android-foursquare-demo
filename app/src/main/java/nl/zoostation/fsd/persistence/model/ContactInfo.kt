package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo

data class ContactInfo(

    @ColumnInfo(name = "phone")
    val phoneNumber: String?,

    @ColumnInfo(name = "twitter")
    val twitterHandle: String?,

    @ColumnInfo(name = "instagram")
    val instagramHandle: String?,

    @ColumnInfo(name = "facebook")
    val facebookHandle: String?
)