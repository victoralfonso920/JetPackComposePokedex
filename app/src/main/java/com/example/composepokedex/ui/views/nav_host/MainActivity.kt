package com.example.composepokedex.ui.views.nav_host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.composepokedex.ui.views.pokemonlist.PokemonListScreen
import com.example.composepokedex.ui.theme.ComposePokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentMain()
        }

    }
}

@Composable
fun ContentMain() {
    ComposePokedexTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "pokemon_list_screen") {
            composable("pokemon_list_screen") {
                PokemonListScreen(navController = navController)
            }
            composable("pokemon_detail_screen",
                arguments = listOf(
                    navArgument("domainColor") {
                        type = NavType.IntType
                    },
                    navArgument("pokemonName") {
                        type = NavType.StringType
                    }
                )) {
                val domainColor = remember {
                    val color = it.arguments?.getInt("pokemon_detail_screen")
                    color?.let { Color(it) } ?: Color.White
                }
                val pokemonName = remember {
                    it.arguments?.getString("pokemonName")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContentMain()
}