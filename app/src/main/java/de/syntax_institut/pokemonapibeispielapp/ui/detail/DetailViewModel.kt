package de.syntax_institut.pokemonapibeispielapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon

class DetailViewModel : ViewModel() {

    private val _currentSelectedPokemon = MutableLiveData<Pokemon>()
    val currentSelectedPokemon: LiveData<Pokemon>
        get() = _currentSelectedPokemon

    fun setSelectedPokemon(pokemon: Pokemon) {
        _currentSelectedPokemon.value = pokemon
    }
}