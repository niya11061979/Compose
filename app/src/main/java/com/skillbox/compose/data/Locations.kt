package com.skillbox.compose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Locations(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "dimension") val dimension: String,
    @Json(name = "created") val created: String
)
