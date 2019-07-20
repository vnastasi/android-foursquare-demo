package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "places", primaryKeys = ["name", "venue_id"])
data class Place(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "venue_id")
    val venueId: String
)