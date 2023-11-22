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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
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
    var pintaPantalla by rememberSaveable { mutableStateOf(0) }
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
    val puntos by rememberSaveable { mutableStateOf(0) }
    Column( modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.casino),
            contentScale = ContentScale.FillHeight
        ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Column(modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                MuestraMano(jugador = jugador1, context)
            }
            Column(modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                MuestraMano(jugador = jugador2, context)
            }
        }
        Column(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = jugador1.calculaPuntos().toString(),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Botones de dame carta y pasar
                Button(modifier = Modifier.wrapContentSize(),
                    onClick = {
                        micarta = miBaraja.cogerCarta()!!
                        jugador1.addCarta(micarta)
                        puntos1 = jugador1.calculaPuntos()}
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
                Button(modifier = Modifier.wrapContentSize(),
                    onClick = {  }
                ) {
                    Text("Pasar ")
                }
            }
        }
    }
}
@Composable
fun MuestraMano(jugador: Jugador, context:Context) {
    var x = 0.dp
    var y = 0.dp
    Box(modifier = Modifier
        .fillMaxHeight(0.7f) // generamos un box
        .fillMaxWidth()) {
        Text(text = "Jugador 2",
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Gray)
                .align(alignment = Alignment.TopCenter))
        for (i in jugador.mano) { // por cada carta generamos otro box que va a tener una imagen dentro
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
/*
@Composable
fun JuegaJugador(
    ancho: Float,
    points: Int,
    onDameCartaClick: () -> Unit,
    onPass: () -> Unit
    ) {
    Column(
        Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(ancho),
    ) {
        Text(
            text = points.toString(),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Botones de dame carta y pasar
            Button(modifier = Modifier.wrapContentSize(),
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
            Button(modifier = Modifier.wrapContentSize(),
                onClick = { onPass() }
            ) {
                Text("Pasar ")
            }
        }
    }
}

 */
