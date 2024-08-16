package de.syntax_institut.pokemonapibeispielapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon

/**
 * Database access object um auf die Room database zuzugreifen
 */
@Dao
interface PokemonDao {

    // nicht suspendete Funktionen MÜSSEN livedata zurückliefern
    @Query("SELECT * FROM pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE favorite = 1")
    fun getAllFavorites(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE types LIKE '%' || :type || '%'") // -> grass, poison die
    // || bedeuten hier NICHT oder sondern sind eine string concatentation
    fun filterByType(type: String): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): LiveData<List<Pokemon>>

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)
}