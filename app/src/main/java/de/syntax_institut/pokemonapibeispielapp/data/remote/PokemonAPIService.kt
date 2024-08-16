package de.syntax_institut.pokemonapibeispielapp.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon
import de.syntax_institut.pokemonapibeispielapp.data.model.PokemonAPIResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

interface PokemonAPIService {

    @GET("pokemon")
    suspend fun getAllPokemon(@Query("limit") limit: Int): PokemonAPIResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon
}

object PokemonApi {
    val service: PokemonAPIService by lazy { retrofit.create(PokemonAPIService::class.java) }
}