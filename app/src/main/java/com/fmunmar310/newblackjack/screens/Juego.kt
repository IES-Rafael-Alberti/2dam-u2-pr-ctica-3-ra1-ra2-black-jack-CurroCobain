package com.fmunmar310.newblackjack.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
@SuppressLint("DiscouragedApi")
@Composable
fun Juego(){
    // inicializamos las variables
    val context = LocalContext.current
    var cartaMostrar by rememberSaveable { mutableStateOf("reverso") }
    val miBaraja = Baraja
    val puntos by rememberSaveable { mutableStateOf(0) }
    //Columna con una imagen , dos botones  y un texto que muestra el número de cartas que quedan en la baraja
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
            Column(modifier = Modifier.fillMaxHeight(0.5f)
                .fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Jugador 1",
                    fontSize = 20.sp,
                    modifier = Modifier.background(Color.Gray))
            }
            Column(modifier = Modifier.fillMaxHeight(0.5f)
                .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Jugador 2",
                    fontSize = 20.sp,
                    modifier = Modifier.background(Color.Gray))
            }

        }
        Row(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = context.resources.getIdentifier(
                        cartaMostrar,
                        "drawable",
                        context.packageName
                    )
                ),
                contentDescription = "Carta mostrada",
                modifier = Modifier
                    .height(300.dp)
                    .width(150.dp)
            )
        }
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){ // Botones de dame carta y reiniciar
            Button(
                onClick = {
                    val micarta = miBaraja.cogerCarta()
                    cartaMostrar = if (micarta == null) {
                        Toast.makeText( context,"No hay más cartas ", Toast.LENGTH_SHORT).show()
                        "reverso"
                    }else
                        "c" + micarta.idDrawable.toString()
                },
            ) {
                Text(text = "Dame una carta ")
            }
            Button(onClick = {
                miBaraja.creaBaraja()
                miBaraja.barajar()
                cartaMostrar = "reverso"
                miBaraja.size = miBaraja.listaCartas.size
            }) {
                Text("Reiniciar ")
            }
        }
    }
}