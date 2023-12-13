package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fmunmar310.newblackjack.R
import com.fmunmar310.newblackjack.cardgames.data.Carta

// Función principal del juego
@SuppressLint("DiscouragedApi", "MutableCollectionMutableState")
@Composable
fun BlackJack(
    navController: NavController,
    blackJackViewModel: BlackJackViewModel
) {
    //Controlamos el uso de las teclas de navegación del móvil
    BackHandler {
        blackJackViewModel.restart()
        navController.popBackStack()
    }
    // Inicializamos los distintos observers que vamos a usar
    val nombre1: String by blackJackViewModel.nombre1.observeAsState(initial = "haz click para editar tu nombre")
    val nombre2: String by blackJackViewModel.nombre2.observeAsState(initial = "haz click para editar tu nombre")
    val mano1: MutableList<Carta> by blackJackViewModel.mano1.observeAsState(initial = mutableListOf())
    val mano2: MutableList<Carta> by blackJackViewModel.mano2.observeAsState(initial = mutableListOf())
    val nombreEditado2: Boolean by blackJackViewModel.nombreEditado2.observeAsState(initial = false)
    val nombreEditado1: Boolean by blackJackViewModel.nombreEditado1.observeAsState(initial = false)
    val plantado1: Boolean by blackJackViewModel.plantado1.observeAsState(initial = false)
    val plantado2: Boolean by blackJackViewModel.plantado2.observeAsState(initial = false)
    val puntos1: Int by blackJackViewModel.puntos1.observeAsState(initial = 0)
    val puntos2: Int by blackJackViewModel.puntos2.observeAsState(initial = 0)
    val ganador: Int by blackJackViewModel.ganador.observeAsState(initial = 0)
    val barajaSize : Int by blackJackViewModel.barajaSize.observeAsState(initial = 52)
    val restart : Int by blackJackViewModel.restart.observeAsState(initial = 0)
    val turno: Int by blackJackViewModel.turno.observeAsState(initial = 1)

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
        //Comprobamos si hay ganador cada vez que se pinta de nuevo la pantalla
        if(ganador == 1){
            Ganador(blackJackViewModel = blackJackViewModel, nombre = nombre1)
        }else if(ganador == 2){
            Ganador(blackJackViewModel = blackJackViewModel, nombre = nombre2)
        }else if(ganador == 3){
            Empate(blackJackViewModel = blackJackViewModel)
        }
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
                MuestraMano(nombreEditado1,nombre1, mano1, barajaSize,blackJackViewModel,1)
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
                MuestraMano(nombreEditado2,nombre2, mano2, barajaSize, blackJackViewModel, 2)
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
            JuegaJugador(1,
                0.5f,
                onDameCartaClick = {
                    blackJackViewModel.dameCarta(1)
                    blackJackViewModel.sumaRestart()
                    blackJackViewModel.winBet(puntos1,puntos2,plantado1,plantado2)
                },
                onPass = {
                    blackJackViewModel.plantarse(1, turno)
                    blackJackViewModel.sumaRestart()
                    blackJackViewModel.winBet(puntos1,puntos2,plantado1,plantado2)
                },
                restart = restart,
                turno = turno
            )
            // --------------------- botones jugador2 ---------------------------
            JuegaJugador(2,
                1f,
                onDameCartaClick = {
                    blackJackViewModel.dameCarta(2)
                    blackJackViewModel.sumaRestart()
                    blackJackViewModel.winBet(puntos1,puntos2,plantado1,plantado2)
                },
                onPass = {
                    blackJackViewModel.plantarse(2, turno)
                    blackJackViewModel.sumaRestart()
                    blackJackViewModel.winBet(puntos1,puntos2,plantado1,plantado2)
                },
                restart = restart,
                turno = turno
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
 * Función composable que muestra  el nombre  y las cartas de la mano del jugador, 
 * permite editar el nombre del jugador haciendo click sobre el propio nombre
 */
@SuppressLint("DiscouragedApi")
@Suppress("UNUSED_PARAMETER")
@Composable
fun MuestraMano(nombreEditado: Boolean, nombre: String, mano: MutableList<Carta>, barajaSize: Int, blackJackViewModel: BlackJackViewModel, num: Int) {
    var x = 0.dp
    var y = 0.dp
    // generamos un box
    Box(modifier = Modifier
        .fillMaxHeight(0.7f)
        .fillMaxWidth()) {
        //Si el nombre no ha sido editado muestra un alertDialog para editarlo
        if(!nombreEditado){
            EditaNombre(blackJackViewModel = blackJackViewModel, num = num)
        }else {
            Text(text = nombre,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.Gray)
                    .align(alignment = Alignment.TopCenter)
                    .clickable { blackJackViewModel.falseaNombreEditado(num) })
        }
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
    jugador: Int,
    ancho: Float,
    restart: Int,
    onDameCartaClick: () -> Unit,
    onPass: () -> Unit,
    turno: Int
) { // -------------------- Columna principal -------------------
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
                //Cuando no es el turno del jugador el botón aparece de color rojo
                colors = if (turno == jugador) ButtonDefaults.buttonColors(Color.Black)
                else ButtonDefaults.buttonColors(Color.Red),
                shape = CutCornerShape(5.dp),
                onClick = { onDameCartaClick() },
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
                //Cuando no es el turno del jugador el botón aparece de color rojo
                colors = if (turno == jugador) ButtonDefaults.buttonColors(Color.Black)
                else ButtonDefaults.buttonColors(Color.Red),
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

/**
 * Función composable que muestra un AlertDialog para editar el nombre del jugador
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaNombre(blackJackViewModel: BlackJackViewModel, num: Int){
    var  nuevoNombre by rememberSaveable{ mutableStateOf("") }
    AlertDialog(
        // Si pulsamos fuera asigna un valor predeterminado
        onDismissRequest = {
           blackJackViewModel.cambiaNombre("jugador", num)
        },
        title = {
            Text("Nombre del jugador $num")
        },
        text = {
            TextField(
                value = nuevoNombre,
                onValueChange = {nuevoNombre = it
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        // Al confirmar actualiza el nombre del jugador
        confirmButton = {
            Button(
                onClick = {
                    blackJackViewModel.cambiaNombre(nuevoNombre,num)
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}

/**
 * Composable que muestra el ganador de la partida
 */
@Composable
fun Ganador(blackJackViewModel: BlackJackViewModel, nombre: String){
    AlertDialog(
        onDismissRequest = {
            blackJackViewModel.restart()
        },
        title = {
            Text("Resultado: ")
        },
        text = {
            Text(
                text = "Ha ganado el jugador $nombre"
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    blackJackViewModel.restart()
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}

/**
 * Composable que se muestra cuando hay un empate en la partida
 */
@Composable
fun Empate(blackJackViewModel: BlackJackViewModel){
    AlertDialog(
        onDismissRequest = {
            blackJackViewModel.restart()
        },
        title = {
            Text("Resultado: ")
        },
        text = {
            Text(
                text = "Empate"
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    blackJackViewModel.restart()
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}






