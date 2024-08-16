package de.syntax_institut.pokemonapibeispielapp.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.pokemonapibeispielapp.data.Repository
import de.syntax_institut.pokemonapibeispielapp.data.local.getInstance
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon
import de.syntax_institut.pokemonapibeispielapp.data.remote.PokemonApi
import kotlinx.coroutines.launch

class ListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = Repository(
        PokemonApi,
        getInstance(application.applicationContext)
    )

    val pokemonList = repo.pokemonList
    val loadingStatus = repo.loadingStatus
    val loadingProgress = repo.loadingProgress

    fun getAllPokemon() {
        viewModelScope.launch {
            repo.getAllPokemon()
        }
    }

    fun updatePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            repo.updatePokemon(pokemon)
        }
    }

    fun setType(type: String) {
        repo.setType(type)
    }

    fun filterByName(name: String) {
        repo.filterByName(name)
    }
}