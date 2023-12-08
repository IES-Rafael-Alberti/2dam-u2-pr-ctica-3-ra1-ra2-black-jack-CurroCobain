package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

/**
 * Composable del juego Carta más alta
 */
@SuppressLint("DiscouragedApi")
@Composable
fun CartaMasAlta(
    navController: NavController,
    cartaMasAltaViewModel: CartaMasAltaViewModel
){
    BackHandler {
        cartaMasAltaViewModel.restart()
        navController.popBackStack()
    }
    val miCarta: String by cartaMasAltaViewModel.imageId.observeAsState(initial= "")
    //Columna con una imagen , dos botones  y un texto que muestra el número de cartas que quedan en la baraja
    Column( modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = LocalContext.current.resources.getIdentifier(
                "casino",
                "drawable",
                LocalContext.current.packageName
            )),
            contentScale = ContentScale.FillHeight
        ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painter = painterResource(id = LocalContext.current.resources.getIdentifier(
            "c$miCarta",
            "drawable",
            LocalContext.current.packageName
        )),
            contentDescription = "Carta mostrada",
            modifier = Modifier
                .height(600.dp)
                .width(300.dp)
        )
        Row(
            Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){ // Botones de dame carta y reiniciar
            Button(
                onClick = {
                    cartaMasAltaViewModel.dameCartaId()
                }

            ) {
                Text(text = "Dame una carta ")
            }
            Button(onClick = {
                cartaMasAltaViewModel.restart()
            }) {
                Text("Reiniciar ")
            }
        }
        Row(modifier = Modifier.padding(top = 50.dp),
            horizontalArrangement = Arrangement.Center
            ){
            // Texto que indica cuantas cartas quedan en la baraja
            Text(text = "Quedan ${cartaMasAltaViewModel.restoDeCartas()} cartas en la baraja",
                modifier = Modifier
                    .background(color = Color.White),
                fontSize = 20.sp
                )
        }
    }
}
