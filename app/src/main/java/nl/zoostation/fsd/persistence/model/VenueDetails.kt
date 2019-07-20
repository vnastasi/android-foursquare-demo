package nl.zoostation.fsd.persistence.model

import androidx.room.Embedded
import androidx.room.Relation

data class VenueDetails(

    @Embedded
    val venue: Venue,

    @Relation(entity = Photo::class, entityColumn = "venue_id", parentColumn = "venue_id" )
    val photos: List<Photo>
)