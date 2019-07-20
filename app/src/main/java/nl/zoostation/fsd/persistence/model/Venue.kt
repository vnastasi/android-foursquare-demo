package nl.zoostation.fsd.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
data class Venue(

    @PrimaryKey
    @ColumnInfo(name = "venue_id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "category")
    val category: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Float? = null,

    @ColumnInfo(name = "incomplete")
    val incomplete: Boolean = true,

    @Embedded
    val location: Location,

    @Embedded
    val contactInfo: ContactInfo? = ContactInfo()
)