package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fmunmar310.newblackjack.R
import com.fmunmar310.newblackjack.cardgames.data.Carta
import com.fmunmar310.newblackjack.cardgames.data.Jugador

// Función principal del juego
@SuppressLint("DiscouragedApi", "MutableCollectionMutableState")
@Composable
fun BlackJack(
    navController: NavController,
    blackJackViewModel: BlackJackViewModel
) {
    BackHandler {
        blackJackViewModel.restart()
        navController.popBackStack()
    }

    val nombre1: String by blackJackViewModel.nombre1.observeAsState(initial = "jugador1")
    val nombre2: String by blackJackViewModel.nombre2.observeAsState(initial = "jugador2")
    val mano1: MutableList<Carta> by blackJackViewModel.mano1.observeAsState(initial = mutableListOf())
    val mano2: MutableList<Carta> by blackJackViewModel.mano2.observeAsState(initial = mutableListOf())
    val plantado1: Boolean by blackJackViewModel.plantado1.observeAsState(initial = false)
    val plantado2: Boolean by blackJackViewModel.plantado2.observeAsState(initial = false)
    val puntos1: Int by blackJackViewModel.puntos1.observeAsState(initial = 0)
    val puntos2: Int by blackJackViewModel.puntos2.observeAsState(initial = 0)
    val barajaSize : Int by blackJackViewModel.barajaSize.observeAsState(initial = 52)
    val restart : Int by blackJackViewModel.restart.observeAsState(initial = 0)

    // ---------------------------------- Columna principal ------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.casino),
                contentScale = ContentScale.FillHeight
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                MuestraMano(nombre1, mano1, barajaSize)
                Spacer(modifier = Modifier.padding(top = 20.dp))
                MuestraStats(1, blackJackViewModel)

            }
            // --------------------- Columna jugador2 -------------------
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.65f)
                    .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                MuestraMano(nombre2, mano2, barajaSize)
                Spacer(modifier = Modifier.padding(top = 20.dp))
                MuestraStats(2, blackJackViewModel)
            }
        }

        // ------------------------- fila botones --------------------------------
        Row(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
        ) {
            // --------------------- botones jugador1 ---------------------------
            JuegaJugador(0.5f,
                onDameCartaClick = {
                    blackJackViewModel.dameCarta(1)
                    blackJackViewModel.sumaRestart()
                },
                onPass = {
                    blackJackViewModel.plantarse(1)
                },
                restart = restart
            )
            // --------------------- botones jugador2 ---------------------------
            JuegaJugador(1f,
                onDameCartaClick = {
                    blackJackViewModel.dameCarta(2)
                    blackJackViewModel.sumaRestart()
                },
                onPass = {
                    blackJackViewModel.plantarse(2)
                },
                restart = restart
            )
        }
        BotonRestart(restart = restart,
            onClick = {
                blackJackViewModel.restart()
            }
        )
    }
}
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


/**
 * Función composable que muestra  el nombre  y las cartas de la mano del jugador
 */
@SuppressLint("DiscouragedApi")
@Suppress("UNUSED_PARAMETER")
@Composable
fun MuestraMano(nombre: String, mano: MutableList<Carta>, barajaSize: Int) {
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
        for (i in mano) {
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
                        id = LocalContext.current.resources.getIdentifier(
                            "c${i.idDrawable}",
                            "drawable",
                            LocalContext.current.packageName
                        )
                    ),
                    contentDescription = "Carta mostrada",
                    modifier = Modifier
                        .height(150.dp)
                        .width(75.dp)
                        .padding(top = 50.dp)
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
@Suppress("UNUSED_PARAMETER")
@Composable
fun JuegaJugador(
    ancho: Float,
    restart: Int,
    onDameCartaClick: () -> Unit,
    onPass: () -> Unit,
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
 * Función composable que muestra los puntos de cada jugador
 */
@Composable
fun MuestraStats(jug: Int, blackJackViewModel: BlackJackViewModel)
{
    Row(
        Modifier
            .padding(top = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Puntos ${blackJackViewModel.getPuntos(jug)}",
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
        )
    }
}

/**
 * Función composable que muestra el botón de reiniciar partida
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun BotonRestart(
    restart: Int,
    onClick: () -> Unit){
    Row(
        Modifier
            .padding(top = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Button(modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = CutCornerShape(5.dp),
            onClick = { onClick()}
        ) {
            Text("Reiniciar partida")
        }
    }
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

 fun reiniciarValores(lista1: MutableList<Carta>, lista2: MutableList<Carta>){
        lista1.clear()
        lista2.clear()
    }
 */



