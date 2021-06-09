package com.example.composepokedex.data.remote

import com.example.composepokedex.data.remote.responses.Pokemon
import com.example.composepokedex.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("") limit:Int,
        @Query("") offset:Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name:String
    ): Pokemon

}