package com.fmunmar310.newblackjack.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fmunmar310.newblackjack.R
import com.fmunmar310.newblackjack.clases.*

// Función principal del juego
@Preview (showBackground = true)
@SuppressLint("DiscouragedApi", "MutableCollectionMutableState")
@Composable
fun Juego(){
    // inicializamos las variables
    val lista1 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    val lista2 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    var puntos1 by rememberSaveable { mutableStateOf(0) }
    var puntos2 by rememberSaveable { mutableStateOf(0) }
    var nombre1 by rememberSaveable { mutableStateOf("jugador1") }
    var nombre2 by rememberSaveable { mutableStateOf("jugador2") }
    var fichas1 by rememberSaveable { mutableStateOf(5) }
    var fichas2 by rememberSaveable { mutableStateOf(5) }
    val banca by rememberSaveable { mutableStateOf(0) }
    var plantado1 by rememberSaveable { mutableStateOf(false) }
    var plantado2 by rememberSaveable { mutableStateOf(false) }
    //val jugador1 = Jugador("jugador1", puntos1, lista1) ------ para la versión con objetos --------
    //val jugador2 = Jugador("jugador2", puntos2, lista2) ------ para la versión con objetos --------
    var victoria1 by rememberSaveable { mutableStateOf(false) }
    var victoria2 by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    var micarta : Carta
    var cartaMostrar by rememberSaveable { mutableStateOf("reverso") }
    val miBaraja = Baraja
    var apuesta by rememberSaveable { mutableStateOf(0) }
    // ---------------------------------- Columna principal ------------
    Column( modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.casino),
            contentScale = ContentScale.FillHeight
        ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(  // ------------------- Fila cartas y puntos --------------------
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){//
            // --------------------- Columna jugador1 ------------------
            Column(modifier = Modifier
                .fillMaxHeight(0.65f)
                .fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                MuestraMano(lista1, nombre1, context)
                MuestraStats(lista1, fichas1)

            }
            // --------------------- Columna jugador2 -------------------
            Column(modifier = Modifier
                .fillMaxHeight(0.65f)
                .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                MuestraMano(lista2, nombre2, context)
                MuestraStats(lista2, fichas2)
            }
        }
        Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)){
            Text(text = "Bote $apuesta",
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.Gray)
                )
        }
        // ------------------------- fila botones --------------------------------
        Row(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()){
            // --------------------- botones jugador1 ---------------------------
            JuegaJugador(0.5f,
                onDameCartaClick = {
                    micarta = miBaraja.cogerCarta()!!
                    lista1.add(micarta)
                    puntos1 = calculaPuntos(lista1)
                    //jugador1.addCarta(micarta) ---versión objetos---
                    //puntos1 = jugador1.calculaPuntos() ---versión objetos ----
                    /* REVISAR NO FUNCIONA
                    victoria = winBet(jugador1, jugador2, banca, apuesta)
                    if (victoria){
                        winBet(jugador1, jugador2, banca, apuesta)
                        }

                     */
                    },
                onPass = { plantado1 = true
                         },
                onBet = {fichas1--
                        apuesta++})
            // --------------------- botones jugador2 ---------------------------
            JuegaJugador(1f,
                onDameCartaClick = {
                    micarta = miBaraja.cogerCarta()!!
                    lista2.add(micarta)
                    puntos2 = calculaPuntos(lista2)
                    /* REVISAR NO FUNCIONA
                    victoria = winBet(jugador1, jugador2, banca, apuesta)
                    if (victoria){
                        winBet(jugador1, jugador2, banca, apuesta)
                        }

                     */
                    },
                onPass = {plantado2 = true
                         },
                onBet = {fichas2--
                        apuesta++})
        }
    }
}
/**
 * @return devuelve un int que indica el ganador de la partida
 * 1 -> jugador 1 gana
 * 2 -> jugador 2 gana
 * 3 -> banca gana
 */
fun winBet(puntos1: Int, puntos2: Int, plantado1: Boolean, plantado2: Boolean): Int
{
    var winner = 0
    if(plantado1 && plantado2){
        if(puntos1 > 21  && puntos2 < 21){
            winner = 2
        }else if( puntos1 < 21 && puntos2 > 21){
            winner = 1
        }else if (puntos1 < 21 && puntos2 < 21){
            if(21 - puntos1 > 21 - puntos2){
                winner = 2
            }else if(21 - puntos1 < 21 - puntos2){
                winner = 1
            }
        }else if(puntos1 == 21){
            winner = 1
        }else if(puntos2 == 21){
            winner = 2
        }else{
            winner = 3
        }
    }else{
        winner = 3
    }
    return winner
}
/*
Pasado a estados, falta determinar ganador y actualizar fichas
 */


