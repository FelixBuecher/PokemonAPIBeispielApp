package de.syntax_institut.pokemonapibeispielapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<Types>,
    var favorite: Boolean = false
)
