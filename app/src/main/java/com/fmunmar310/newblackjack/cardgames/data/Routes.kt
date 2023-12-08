package com.fmunmar310.newblackjack.cardgames.data

/**
 * Sealed class que indica las diferentes rutas de navegación en la app
 * @property route identifica cada ruta en la navegación
 */
sealed class Routes(val route: String) {
    object Inicio : Routes("inicio")
    object CartaMasAlta : Routes("cartaMasAlta")
    object BlackJack : Routes("blackJack")
}