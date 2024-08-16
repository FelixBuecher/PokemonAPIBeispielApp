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
    // Hält zu jeder Zeit die informationen aller in Room gespeicherten Pokemon.
    // Dies ist hauptsächlich wichtig zum checken ob es neue Pokemon gibt.
    private val allPokemon = database.dao.getAll()
    // Livedata die dafür dient unserer richtigen Pokemonliste, welche wir später anzeigen, mitzuteilen, was angezeigt werden soll
    private val selectedType = MutableLiveData("All")

    // Ein "Switch", welcher als Beobachtungspunkt agiert, und je nachdem, welcher Wert in selectedType steht, eine andere Livedata weiterreicht,
    // Es muss mit switchmap passieren, da beim einfachen überschreiben die observer verloren gehen.
    val pokemonList = selectedType.switchMap { type ->
        if(type.startsWith("NAME:")) database.dao.filterByName(type.drop(5))
        else if (type == "All") allPokemon
        else database.dao.filterByType(type)
    }
    // Livedata alles favorites, da in einer anderen view, brauchen wir eine eigene livedata
    val favorites = database.dao.getAllFavorites()

    // Status und progress für den ladebildschirm
    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _loadingProgress = MutableLiveData(0.0)
    val loadingProgress: LiveData<Double>
        get() = _loadingProgress

    suspend fun getAllPokemon() {
        // Lade die ersten 550 pokemon
        val result = api.service.getAllPokemon(550)
        // Wenn die menge in der Datenbank nicht mit der Menge in der DB übereinstimmt, dann fügen wir die fehlenden pokemon hinzu
        if(allPokemon.value?.size != result.results.size) {
            _loadingStatus.postValue(LoadingStatus.LOADING)
            val pokemonToLoad = result.results.size - allPokemon.value!!.size
            var pokemonAlreadyLoaded = 0
            result.results.forEach { entry ->
                // falls das derzeitig betrachtete pokemon noch nicht in der databse ist, lade es rein
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