package com.fmunmar310.newblackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fmunmar310.newblackjack.R

@Preview
@Composable
fun Inicio(){
    val context = LocalContext.current
    Column( modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.casino),
            contentScale = ContentScale.FillHeight
        ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "BLACKJACK",
            fontSize = 60.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 20.dp)
        )
        Image(
            painter = painterResource(
                id = R.drawable.blackjack
            ),
            contentDescription = "Inicio",
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )
        Text(text = "Modos de juego",
            fontSize = 60.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 50.dp)
        )
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = CutCornerShape(5.dp),
            modifier = Modifier.padding(top = 50.dp)
                .width(250.dp))
        {
            Text(text = "Dos jugadores",
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Black
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = CutCornerShape(5.dp),
            modifier = Modifier.padding(top = 20.dp)
                .width(250.dp))
        {
            Text(text = "Vs Máquina",
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Black
            )
        }
        Spacer(modifier = Modifier.padding(50.dp))
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = CutCornerShape(5.dp),
            modifier = Modifier.width(200.dp))
        {
            Text(text = "Salir",
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Black
            )
        }
    }
}

//Faltan comentarios y navegación