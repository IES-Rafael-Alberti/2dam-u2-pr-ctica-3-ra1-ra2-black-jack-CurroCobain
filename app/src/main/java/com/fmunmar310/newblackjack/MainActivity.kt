package com.fmunmar310.newblackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fmunmar310.newblackjack.cardgames.data.Routes
import com.fmunmar310.newblackjack.cardgames.ui.BlackJackViewModel
import com.fmunmar310.newblackjack.cardgames.ui.CartaMasAltaViewModel
import com.fmunmar310.newblackjack.cardgames.ui.*
import com.fmunmar310.newblackjack.ui.theme.NewBlackJackTheme

class MainActivity : ComponentActivity() {
    private val cartaMasAltaViewModel: CartaMasAltaViewModel by viewModels()
    private val blackJackViewModel : BlackJackViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewBlackJackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Inicio.route
                    ) {
                        composable(Routes.Inicio.route) {
                            Inicio(
                                navController = navController
                            )
                        }
                        composable(Routes.CartaMasAlta.route) {
                            CartaMasAlta(
                                navController = navController,
                                cartaMasAltaViewModel = cartaMasAltaViewModel
                            )
                        }
                        composable(Routes.BlackJack.route) {
                            BlackJack(
                                navController = navController,
                                blackJackViewModel = blackJackViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
