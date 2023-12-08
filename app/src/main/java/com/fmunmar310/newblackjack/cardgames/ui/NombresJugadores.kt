package com.fmunmar310.newblackjack.cardgames.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fmunmar310.newblackjack.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NombresJugadores(){
    Column( modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.casino),
            contentScale = ContentScale.FillHeight
        ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TextField(value = "", onValueChange = {it})
    }
}

/*
Falta completar navegación
 */