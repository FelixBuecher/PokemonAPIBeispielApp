package de.syntax_institut.pokemonapibeispielapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val dao: PokemonDao
}

private lateinit var INSTANCE: PokemonDatabase

fun getInstance(context: Context): PokemonDatabase {
    synchronized(PokemonDatabase::class) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_database"
            ).build()
        }
        return INSTANCE
    }
}