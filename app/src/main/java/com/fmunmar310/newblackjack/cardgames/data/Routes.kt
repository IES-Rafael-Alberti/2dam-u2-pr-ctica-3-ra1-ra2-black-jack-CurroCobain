package com.fmunmar310.newblackjack.cardgames.data

import androidx.lifecycle.AndroidViewModel
import com.fmunmar310.newblackjack.cardgames.ui.BlackJackViewModel

/**
 * Sealed class que indica las diferentes rutas de navegación en la app
 * @property route identifica cada ruta en la navegación
 */
sealed class Routes(val route: String) {
    object Inicio : Routes("inicio")
    object CartaMasAlta : Routes("cartaMasAlta")
    object BlackJackVsMaquina : Routes ("vsMaquina")
    object BlackJack : Routes("blackJack")
    object VsMAquina : Routes("vsMaquina")
    object ModosDeJuegoBlack : Routes ("modos")
}