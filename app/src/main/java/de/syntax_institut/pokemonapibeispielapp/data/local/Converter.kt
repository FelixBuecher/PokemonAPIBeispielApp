package de.syntax_institut.pokemonapibeispielapp.data.local

import androidx.room.TypeConverter
import de.syntax_institut.pokemonapibeispielapp.data.model.Sprites
import de.syntax_institut.pokemonapibeispielapp.data.model.Type
import de.syntax_institut.pokemonapibeispielapp.data.model.Types

class Converter {

    @TypeConverter
    fun SpritesToString(sprites: Sprites): String = sprites.image

    @TypeConverter
    fun StringToSprites(string: String): Sprites = Sprites(string)

    @TypeConverter
    fun TypesListToString(types: List<Types>): String = types.joinToString { it.type.name }

    @TypeConverter
    fun StringToTypesList(string: String): List<Types> = string.split(",").map { Types(Type(it.trim())) }

// "grass, poison" -> "grass" " poison" -> listof(Types(Type("grass")), Types(Type("poison")))
}