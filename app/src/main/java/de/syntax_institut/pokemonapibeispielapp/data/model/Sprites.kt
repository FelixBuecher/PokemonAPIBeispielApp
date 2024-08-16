package de.syntax_institut.pokemonapibeispielapp.data.model

import com.squareup.moshi.Json

data class Sprites(
    @Json(name = "front_default")
    val image: String
)
