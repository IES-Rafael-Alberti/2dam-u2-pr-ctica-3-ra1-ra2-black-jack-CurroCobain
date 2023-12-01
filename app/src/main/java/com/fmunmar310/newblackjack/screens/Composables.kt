package com.fmunmar310.newblackjack.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fmunmar310.newblackjack.clases.Carta


/**
 * Función composable que muestra  el nombre  y las cartas de la mano del jugador
 */
@Composable
fun MuestraMano(jugador: MutableList<Carta>, nombre: String, context: Context, lista: MutableList<Carta>, fichas: Int ){
    var x = 0.dp
    var y = 0.dp
    var restoDeCartas by rememberSaveable { mutableStateOf(0) }
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
            restoDeCartas++
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