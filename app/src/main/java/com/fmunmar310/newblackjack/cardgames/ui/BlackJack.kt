package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fmunmar310.newblackjack.R
import com.fmunmar310.newblackjack.cardgames.data.Baraja
import com.fmunmar310.newblackjack.cardgames.data.Carta
import com.fmunmar310.newblackjack.cardgames.data.Routes

// Función principal del juego
@SuppressLint("DiscouragedApi", "MutableCollectionMutableState")
@Composable
fun BlackJack(
    navController: NavController,
    blackJackViewModel: BlackJackViewModel
) {
    // inicializamos las variables
    val lista1 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    val lista2 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    var puntos1 by rememberSaveable { mutableStateOf(0) }
    var puntos2 by rememberSaveable { mutableStateOf(0) }
    val nombre1 by rememberSaveable { mutableStateOf("jugador1") }
    val nombre2 by rememberSaveable { mutableStateOf("jugador2") }
    var fichas1 by rememberSaveable { mutableStateOf(5) }
    var fichas2 by rememberSaveable { mutableStateOf(5) }
    //val banca by rememberSaveable { mutableStateOf(0) }
    var plantado1 by rememberSaveable { mutableStateOf(false) }
    var plantado2 by rememberSaveable { mutableStateOf(false) }
    //val jugador1 = Jugador("jugador1", puntos1, lista1) ------ para la versión con objetos --------
    //val jugador2 = Jugador("jugador2", puntos2, lista2) ------ para la versión con objetos --------
    val context = LocalContext.current
    var micarta: Carta
    var cartaMostrar by rememberSaveable { mutableStateOf("reverso") }
    val miBaraja = Baraja
    var apuesta by rememberSaveable { mutableStateOf(0) }
    var finPartida by rememberSaveable { mutableStateOf(false) }
    /*
    while (!finPartida) {
        if (winBet(puntos1, puntos2, plantado1, plantado2) == 1) {
            puntos1 += apuesta
            apuesta = 0
            miBaraja.reiniciar()
            reiniciarValores(lista1, lista2)
        } else if (winBet(puntos1, puntos2, plantado1, plantado2) == 2) {
            puntos2 += apuesta
            apuesta = 0
            miBaraja.reiniciar()
            reiniciarValores(lista1, lista2)
        } else if (fichas1 == 0 || fichas2 == 0) {
            finPartida = true
        }

     */
        // ---------------------------------- Columna principal ------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.casino),
                    contentScale = ContentScale.FillHeight
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(  // ------------------- Fila cartas y puntos --------------------
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {//
                // --------------------- Columna jugador1 ------------------
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.65f)
                        .fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    MuestraMano(lista1, nombre1, context)
                    MuestraStats(lista1, fichas1)

                }
                // --------------------- Columna jugador2 -------------------
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.65f)
                        .fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    MuestraMano(lista2, nombre2, context)
                    MuestraStats(lista2, fichas2)
                }
            }
            Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                Text(
                    text = "Bote $apuesta",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .background(Color.Gray)
                )
            }
            // ------------------------- fila botones --------------------------------
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                // --------------------- botones jugador1 ---------------------------
                JuegaJugador(0.5f,
                    onDameCartaClick = {
                        micarta = miBaraja.cogerCarta()!!
                        lista1.add(micarta)
                        puntos1 = calculaPuntos(lista1)
                        //jugador1.addCarta(micarta) ---versión objetos---
                        //puntos1 = jugador1.calculaPuntos() ---versión objetos ----

                    },
                    onPass = {
                        plantado1 = true
                    },
                    onBet = {
                        fichas1--
                        apuesta++
                    })
                // --------------------- botones jugador2 ---------------------------
                JuegaJugador(1f,
                    onDameCartaClick = {
                        micarta = miBaraja.cogerCarta()!!
                        lista2.add(micarta)
                        puntos2 = calculaPuntos(lista2)

                    },
                    onPass = {
                        plantado2 = true
                    },
                    onBet = {
                        fichas2--
                        apuesta++
                    })
            }
        }
    }
//}


    /**
     * @return devuelve un int que indica el ganador de la partida
     * 1 -> jugador 1 gana
     * 2 -> jugador 2 gana
     * 3 -> banca gana
     */
    fun winBet(puntos1: Int, puntos2: Int, plantado1: Boolean, plantado2: Boolean): Int {
        var winner = 0
        if (plantado1 && plantado2) {
            if (puntos1 > 21 && puntos2 < 21) {
                winner = 2
            } else if (puntos1 < 21 && puntos2 > 21) {
                winner = 1
            } else if (puntos1 < 21 && puntos2 < 21) {
                if (21 - puntos1 > 21 - puntos2) {
                    winner = 2
                } else if (21 - puntos1 < 21 - puntos2) {
                    winner = 1
                }
            } else if (puntos1 == 21) {
                winner = 1
            } else if (puntos2 == 21) {
                winner = 2
            } else {
                winner = 3
            }
        } else {
            winner = 3
        }
        return winner
    }

    fun reiniciarValores(lista1: MutableList<Carta>, lista2: MutableList<Carta>){
        lista1.clear()
        lista2.clear()
    }

/**
 * Función composable que muestra  el nombre  y las cartas de la mano del jugador
 */
@Composable
fun MuestraMano(jugador: MutableList<Carta>, nombre: String, context: Context) {
    var x = 0.dp
    var y = 0.dp
    // generamos un box
    Box(modifier = Modifier
        .fillMaxHeight(0.7f)
        .fillMaxWidth()) {
        Text(text = nombre,
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
                .align(alignment = Alignment.TopCenter))
        Spacer(modifier = Modifier.padding(top = 20.dp))
        for (i in jugador) {
            // por cada carta generamos otro box que va a tener una imagen dentro
            Box(modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .offset(
                    x,
                    y
                ) // Esto recoloca el Box en diagonal al anterior cambiando las variables "x" e "y"
                .fillMaxWidth()
                .fillMaxHeight()
            ) {
                Image( // Esta es la imagen de la carta
                    painter = painterResource(
                        id = context.resources.getIdentifier(
                            "c" + i.idDrawable.toString(),
                            "drawable",
                            context.packageName
                        )
                    ),
                    contentDescription = "Carta mostrada",
                    modifier = Modifier
                        .height(150.dp)
                        .width(75.dp)
                )
                x += 15.dp //aumentamos el valor de x
                y += 15.dp // aumentamos el valor de y
            }
        }
    }
}

/**
 * Función composable con tres botones para pedir carta, subir la apuesta o plantarse
 */
@Composable
fun JuegaJugador(
    ancho: Float,
    onDameCartaClick: () -> Unit,
    onBet: () -> Unit,
    onPass: () -> Unit
) {
    Column(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(ancho),
    ) {
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // --------------------------- Botón de dame carta ----------------------------
            Button(modifier = Modifier.wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = CutCornerShape(5.dp),
                onClick = { onDameCartaClick() }
            ) {
                Text(text = "Dame una carta ")
            }
        }
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // --------------------------- Botón para subir apuesta ----------------------------
            Button(modifier = Modifier.wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = CutCornerShape(5.dp),
                onClick = { onBet() }
            ) {
                Text("Subir apuesta ")
            }
        }
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // --------------------------- Botón para plantarse ----------------------------
            Button(modifier = Modifier.wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = CutCornerShape(5.dp),
                onClick = { onPass() }
            ) {
                Text("Plantarse ")
            }
        }
    }
}

/**
 * Función composable que muestra los puntos y las fichas de cada jugador
 */
@Composable
fun MuestraStats(lista: MutableList<Carta>, fichas: Int)
{
    Row(
        Modifier
            .padding(top = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Puntos ${calculaPuntos(lista)}",
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
        )
    }
    Row(
        Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Fichas $fichas",
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
        )
    }
}
/**
 * @return devuelve los puntos de una lista de cartas
 * @see Carta
 */
fun calculaPuntos(mano: MutableList<Carta>):Int{
    var puntos = 0
    if(mano.isNotEmpty()) {
        for (i in mano) {
            if (puntos + i.puntosMax <= 21) {
                puntos += i.puntosMax
            } else {
                puntos += i.puntosMin
            }
        }
    }
    return puntos
}
/*
Pasado a estados, falta determinar ganador y actualizar fichas
  REVISAR NO FUNCIONA
    victoria = winBet(jugador1, jugador2, banca, apuesta)
    if (victoria){
        winBet(jugador1, jugador2, banca, apuesta)
        }

  REVISAR NO FUNCIONA
    victoria = winBet(jugador1, jugador2, banca, apuesta)
    if (victoria){
        winBet(jugador1, jugador2, banca, apuesta)
        }
 */


