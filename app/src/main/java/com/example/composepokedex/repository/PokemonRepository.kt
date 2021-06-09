package com.example.composepokedex.repository

import com.example.composepokedex.data.remote.PokeApi
import com.example.composepokedex.data.remote.responses.Pokemon
import com.example.composepokedex.data.remote.responses.PokemonList
import com.example.composepokedex.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {

    suspend fun getpokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        }catch (e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getpokemonInfo(pokemonName:String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        }catch (e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}