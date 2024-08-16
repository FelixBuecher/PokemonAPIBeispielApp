package de.syntax_institut.pokemonapibeispielapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE favorite = 1")
    fun getAllFavorites(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE types LIKE '%' || :type || '%'") // -> grass, poison die
    // || bedeuten hier NICHT oder sondern sind eine string concatentation
    fun filterByType(type: String): LiveData<List<Pokemon>>

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)
}