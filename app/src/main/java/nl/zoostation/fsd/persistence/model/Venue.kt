package nl.zoostation.fsd.persistence.model

import androidx.room.*

@Entity(tableName = "venues")
data class Venue(

    @PrimaryKey
    @ColumnInfo(name = "venue_id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "rating")
    val rating: Double?,

    @Embedded
    val location: Location,

    @Embedded
    val contactInfo: ContactInfo
)