package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "photos",
    foreignKeys = [ForeignKey(
        entity = Venue::class,
        parentColumns = ["venue_id"],
        childColumns = ["venue_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Photo(

    @PrimaryKey
    @ColumnInfo(name = "photo_id")
    val id: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "venue_id")
    val venueId: String
)