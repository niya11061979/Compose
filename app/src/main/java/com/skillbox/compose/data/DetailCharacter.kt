package com.skillbox.compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailCharacter(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "created") val created: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "image") val posterUrl: String?,
    @Json(name = "location") val location: Location,
    @Json(name = "episode") val episode: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DetailCharacter) return false
        if (!episode.contentEquals(other.episode)) return false
        return true
    }

    override fun hashCode(): Int = episode.contentHashCode()
}