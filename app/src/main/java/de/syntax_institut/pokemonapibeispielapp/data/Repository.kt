package de.syntax_institut.pokemonapibeispielapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import de.syntax_institut.pokemonapibeispielapp.data.local.PokemonDatabase
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon
import de.syntax_institut.pokemonapibeispielapp.data.remote.PokemonApi
import de.syntax_institut.pokemonapibeispielapp.util.LoadingStatus

class Repository(
    private val api: PokemonApi,
    private val database: PokemonDatabase
) {
    private val allPokemon = database.dao.getAll()
    private val selectedType = MutableLiveData("All")
    val pokemonList = selectedType.switchMap { type ->
        if(type.startsWith("NAME:")) database.dao.filterByName(type.drop(5))
        else if (type == "All") allPokemon
        else database.dao.filterByType(type)
    }
    val favorites = database.dao.getAllFavorites()

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _loadingProgress = MutableLiveData(0.0)
    val loadingProgress: LiveData<Double>
        get() = _loadingProgress

    suspend fun getAllPokemon() {
        val result = api.service.getAllPokemon(550)

        if(allPokemon.value?.size != result.results.size) {
            _loadingStatus.postValue(LoadingStatus.LOADING)
            val pokemonToLoad = result.results.size - allPokemon.value!!.size
            var pokemonAlreadyLoaded = 0
            result.results.forEach { entry ->
                if(pokemonList.value?.find { entry.name == it.name } == null) {
                    val pokemon = api.service.getPokemon(entry.name)
                    database.dao.insertPokemon(pokemon)
                    pokemonAlreadyLoaded++
                    _loadingProgress.postValue(pokemonAlreadyLoaded.toDouble() / pokemonToLoad.toDouble())
                }
            }
        }
        _loadingStatus.postValue(LoadingStatus.DONE)
    }

    suspend fun updatePokemon(pokemon: Pokemon) {
        database.dao.updatePokemon(pokemon)
    }

    fun setType(type: String) {
        selectedType.value = type
    }

    fun filterByName(name: String) {
        selectedType.value = "NAME:$name"
    }
}