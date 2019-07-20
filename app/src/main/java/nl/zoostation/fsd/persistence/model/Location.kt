package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class Location(

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "postal_code")
    val postalCode: String,

    @ColumnInfo(name = "address")
    val address: String,

    @Embedded
    val coordinates: Coordinates
)
