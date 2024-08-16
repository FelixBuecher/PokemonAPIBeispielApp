package de.syntax_institut.pokemonapibeispielapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.pokemonapibeispielapp.data.Repository
import de.syntax_institut.pokemonapibeispielapp.data.local.getInstance
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon
import de.syntax_institut.pokemonapibeispielapp.data.remote.PokemonApi
import kotlinx.coroutines.launch

class FavoritesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = Repository(PokemonApi, getInstance(application.applicationContext))

    val favorites = repo.favorites

    fun updatePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            repo.updatePokemon(pokemon)
        }
    }
}