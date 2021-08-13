package com.example.composepokedex.ui.views.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.composepokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Created by Victor Hernandez on 10/8/21.
// Proyect ComposePokedex
//contact victoralfonso920@gmail.com

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repo: PokemonRepository,
): ViewModel() {

    fun calcDominantColor(drawable: Drawable,onFinish: (Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}