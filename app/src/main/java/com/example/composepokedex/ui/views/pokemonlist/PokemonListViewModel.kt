package com.example.composepokedex.ui.views.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.composepokedex.data.models.PokedexListEntry
import com.example.composepokedex.repository.PokemonRepository
import com.example.composepokedex.utils.Constants.PAGE_SIZE
import com.example.composepokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

// Created by Victor Hernandez on 10/8/21.
// Proyect ComposePokedex
//contact victoralfonso920@gmail.com

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repo: PokemonRepository,
): ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            val result = repo.getpokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result){
                is Resource.Success ->{
                    result.data?.let {
                        endReached.value = curPage * PAGE_SIZE >= it.count
                        val pokedexEntries = it.results.mapIndexed { index, entry ->
                            val number = if (entry.url.endsWith("/")){
                                entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                            }else{
                                entry.url.takeLastWhile { it.isDigit() }
                            }
                            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                            PokedexListEntry(entry.name.capitalize(Locale.ROOT),url,number.toInt())
                        }
                        curPage++
                        loadError.value = ""
                        isLoading.value = false
                        pokemonList.value = pokedexEntries
                    }
                }
                is Resource.Error ->{
                    loadError.value = result.message.orEmpty()
                    isLoading.value = false
                }
            }

        }
    }

    fun calcDominantColor(bmp: Bitmap,onFinish: (Color) -> Unit){

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}