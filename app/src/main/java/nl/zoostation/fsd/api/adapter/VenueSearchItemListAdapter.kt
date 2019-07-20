package nl.zoostation.fsd.api.adapter

import com.google.gson.*
import nl.zoostation.fsd.persistence.model.Coordinates
import nl.zoostation.fsd.persistence.model.Location
import nl.zoostation.fsd.persistence.model.Venue
import java.lang.reflect.Type

class VenueSearchItemListAdapter : JsonDeserializer<List<Venue>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Venue> =
        json?.asJsonObject
            ?.getAsJsonObject("response")
            ?.getAsJsonArray("venues")
            ?.asSequence()
            ?.map { it.asJsonObject }
            ?.map { parseJsonVenue(it) }
            ?.toList()
            ?: emptyList()

    private fun parseJsonVenue(jsonVenue: JsonObject): Venue {
        val id = requireNotNull(jsonVenue.getAsJsonPrimitive("id").asString)
        val name = requireNotNull(jsonVenue.getAsJsonPrimitive("name").asString)
        val category = parseCategory(jsonVenue.getAsJsonArray("categories"))
        val location = parseLocation(jsonVenue.getAsJsonObject("location"))

        return Venue(
            id = id,
            name = name,
            category = category,
            location = location
        )
    }

    private fun parseCategory(jsonCategories: JsonArray): String? {
        if (jsonCategories.isJsonNull || jsonCategories.size() == 0) {
            return null
        }
        return jsonCategories[0].asJsonObject.getAsJsonPrimitive("name").asString
    }

    private fun parseLocation(jsonLocation: JsonObject): Location {
        val address = jsonLocation.getAsJsonPrimitive("address").asString
        val postalCode = jsonLocation.getAsJsonPrimitive("postalCode").asString
        val city = jsonLocation.getAsJsonPrimitive("city").asString
        val state = jsonLocation.getAsJsonPrimitive("state").asString
        val country = jsonLocation.getAsJsonPrimitive("country").asString
        val latitude = jsonLocation.getAsJsonPrimitive("lat").asDouble
        val longitude = jsonLocation.getAsJsonPrimitive("lng").asDouble

        return Location(
            address = address,
            postalCode = postalCode,
            city = city,
            state = state,
            country = country,
            coordinates = Coordinates(latitude, longitude)
        )
    }
}