package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo

data class Coordinates(

    @ColumnInfo(name = "lat")
    val latitude: Double,

    @ColumnInfo(name = "lng")
    val longitude: Double
)