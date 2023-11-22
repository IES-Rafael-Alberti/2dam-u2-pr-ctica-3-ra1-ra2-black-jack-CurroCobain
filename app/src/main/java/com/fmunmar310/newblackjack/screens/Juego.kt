package com.fmunmar310.newblackjack.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.fmunmar310.newblackjack.R
import com.fmunmar310.newblackjack.clases.*

// Función principal del juego
@Preview (showBackground = true)
@SuppressLint("DiscouragedApi")
@Composable
fun Juego(){
    // inicializamos las variables
    val lista1 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    val lista2 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
    var puntos1 by rememberSaveable { mutableStateOf(0) }
    var puntos2 by rememberSaveable { mutableStateOf(0) }
    val jugador1 = Jugador("jugador1", puntos1, lista1)
    val jugador2 = Jugador("jugador2", puntos2, lista2)
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
                MuestraMano(jugador = jugador1, context)
                MuestraStats(jugador = jugador1)

            }
            // --------------------- Columna jugador2 -------------------
            Column(modifier = Modifier
                .fillMaxHeight(0.65f)
                .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                MuestraMano(jugador = jugador2, context)
                MuestraStats(jugador = jugador2)
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
                    jugador1.addCarta(micarta)
                    puntos1 = jugador1.calculaPuntos()
                    },
                onPass = {},
                onBet = {jugador1.apuesta(1)
                apuesta++})
            // --------------------- botones jugador2 ---------------------------
            JuegaJugador(1f,
                onDameCartaClick = {
                    micarta = miBaraja.cogerCarta()!!
                    jugador2.addCarta(micarta)
                    puntos2 = jugador2.calculaPuntos()
                    },
                onPass = {},
                onBet = {jugador2.apuesta(1)
                apuesta++})
        }
    }
}

/**
 * Función composable que muestra  el nombre  y las cartas de la mano del jugador
 */
@Composable
fun MuestraMano(jugador: Jugador, context:Context) {
    var x = 0.dp
    var y = 0.dp
    // generamos un box
    Box(modifier = Modifier
        .fillMaxHeight(0.7f)
        .fillMaxWidth()) {
        Text(text = jugador.nombre,
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
                .align(alignment = Alignment.TopCenter))
        Spacer(modifier = Modifier.padding(top = 20.dp))
        for (i in jugador.mano) {
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
fun MuestraStats(
    jugador: Jugador
){
    Row(
        Modifier
            .padding(top = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Puntos ${jugador.calculaPuntos()}",
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
        Text(text = "Fichas ${jugador.fichas}",
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
        )
    }
}
