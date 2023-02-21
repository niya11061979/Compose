package com.skillbox.compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Characters(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "url") val url: String?,
    @Json(name = "image") val posterUrl: String?,
    @Json(name = "location") val location: Location
)
