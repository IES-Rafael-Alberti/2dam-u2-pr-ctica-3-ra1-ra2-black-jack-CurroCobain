package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmunmar310.newblackjack.cardgames.data.Baraja
import com.fmunmar310.newblackjack.cardgames.data.Carta
import com.fmunmar310.newblackjack.cardgames.data.Naipes
import com.fmunmar310.newblackjack.cardgames.data.Palos

class BlackJackViewModel (application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _puntos1  = MutableLiveData<Int>()
    val puntos1 : LiveData<Int> = _puntos1

    private val _mano1 = MutableLiveData<MutableList<Carta>>()
    val mano1 : LiveData<MutableList<Carta>> = _mano1

    private val _puntos2  = MutableLiveData<Int>()
    val puntos2 : LiveData<Int> = _puntos2

    private val _mano2 = MutableLiveData<MutableList<Carta>>()
    val mano2 : LiveData<MutableList<Carta>> = _mano2

    private val _nombre1 = MutableLiveData<String>()
    val nombre1: LiveData<String> = _nombre1

    private val _nombreEditado1 = MutableLiveData<Boolean>()
    val nombreEditado1: LiveData<Boolean> = _nombreEditado1

    private val _nombreEditado2 = MutableLiveData<Boolean>()
    val nombreEditado2: LiveData<Boolean> = _nombreEditado2

    private val _nombre2 = MutableLiveData<String>()
    val nombre2: LiveData<String> = _nombre2

    private val _plantado1 = MutableLiveData<Boolean>()
    val plantado1: LiveData<Boolean> = _plantado1

    private val _plantado2 = MutableLiveData<Boolean>()
    val plantado2: LiveData<Boolean> = _plantado2

    private val _ganador = MutableLiveData<Int>()
    val ganador : LiveData<Int> = _ganador

    private val miBaraja = Baraja

    private  val _barajaSize = MutableLiveData<Int>()
    val barajaSize : LiveData<Int> = _barajaSize

    private val _restart = MutableLiveData<Int>()
    val restart : LiveData<Int> = _restart

    init {
        _restart.value = 0
        restart()
        _mano1.value = mutableListOf()
        _mano2.value = mutableListOf()
        _nombreEditado1.value = false
        _nombreEditado2.value = false
    }
    fun cambiaNombre(nuevoNombre: String, num: Int){
        if(num == 1){
            _nombre1.value = nuevoNombre
            _nombreEditado1.value = true
        }else if(num == 2){
            _nombre2.value = nuevoNombre
            _nombreEditado2.value = true
        }
    }
    fun falseaNombreEditado(num: Int){
        if(num == 1){
            _nombreEditado1.value = false
        }else if (num == 2){
            _nombreEditado2.value = false
        }
    }
    fun dameCarta(jug: Int){
        val nuevaCarta = miBaraja.cogerCarta()
        if(jug == 1) {
            if (!_plantado1.value!!) {
                _mano1.value?.add(nuevaCarta!!)
                _puntos1.value = calculaPuntos(_mano1.value!!)
            } else {
                toasted("El jugador está plantado")
            }
        }else if(jug == 2){
            if (!_plantado2.value!!) {
                _mano2.value?.add(nuevaCarta!!)
                _puntos2.value = calculaPuntos(_mano2.value!!)
            } else {
                toasted("El jugador está plantado")
            }
        }
        _barajaSize.value = miBaraja.size
    }
    fun getPuntos(jug: Int): Int {
        if(jug == 1){
            return calculaPuntos(_mano1.value!!)
        }else if(jug == 2){
            return calculaPuntos(_mano2.value!!)
        }
        return 0
    }
    fun plantarse(jug: Int){
        if(jug == 1){
            _plantado1.value = true
        }else if(jug == 2){
            _plantado2.value = true
        }
    }
    fun sumaRestart(){
        _restart.value = _restart.value!! + 1
    }
    fun restart(){
        sumaRestart()
        miBaraja.reiniciar(context)
        _mano1.value?.clear()
        _mano2.value?.clear()
        _puntos1.value = 0
        _puntos2.value = 0
        _plantado1.value = false
        _plantado2.value = false
        _ganador.value = 0
    }
    fun calculaPuntos(mano: MutableList<Carta> ): Int {
        var puntos = 0
        if (mano.isNotEmpty()) {
            for (i in mano) {
                // Se calcula si el total de puntos + la carta actual pasan de 21 y se asigna puntosMax o puntosMin en función a ese cálculo
                if (puntos + i.puntosMax <= 21) {
                    puntos += i.puntosMax
                } else {
                    puntos += i.puntosMin
                }
            }
        }
        return puntos
    }
    fun restoDeCartas() = miBaraja.size
    fun toasted(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    /**
     * @return devuelve un int que indica el ganador de la partida
     * 1 -> jugador 1 gana
     * 2 -> jugador 2 gana
     * 3 -> banca gana
     */
    fun winBet(puntos1: Int, puntos2: Int, plantado1: Boolean, plantado2: Boolean){
       if(puntos1 > 21){
           if(puntos2 <= 21){
               _ganador.value = 2
           }
       }else if(puntos2 > 21){
           if(puntos1 <= 21){
               _ganador.value = 1
           }
       }else if(puntos1 < 21 && plantado1){
           if(puntos2 < puntos1 && plantado2){
               _ganador.value = 1
           }
       }else if(puntos2 < 21 && plantado2){
           if(puntos1 < puntos2 && plantado1){
               _ganador.value = 2
           }
       }else if(puntos1 > 21 && puntos2 > 21){
           _ganador.value = 3
       }else{
           _ganador.value = 0
       }
    }

}
/*
 ------------------------------------------ CODIGO RECICLABLE??? ----------------------------------------------------
 // inicializamos las variables
val lista1 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
val lista2 by rememberSaveable { mutableStateOf(mutableListOf<Carta>()) }
var puntos1 by rememberSaveable { mutableStateOf(0) }
var puntos2 by rememberSaveable { mutableStateOf(0) }
val nombre1 by rememberSaveable { mutableStateOf("jugador1") }
val nombre2 by rememberSaveable { mutableStateOf("jugador2") }
var fichas1 by rememberSaveable { mutableStateOf(5) }
var fichas2 by rememberSaveable { mutableStateOf(5) }
val banca by rememberSaveable { mutableStateOf(0) }
var plantado1 by rememberSaveable { mutableStateOf(false) }
var plantado2 by rememberSaveable { mutableStateOf(false) }
val jugador1 = Jugador("jugador1", puntos1, lista1) ------ para la versión con objetos --------
val jugador2 = Jugador("jugador2", puntos2, lista2) ------ para la versión con objetos --------
val context = LocalContext.current
var micarta: Carta
var cartaMostrar by rememberSaveable { mutableStateOf("reverso") }
val miBaraja = Baraja
var apuesta by rememberSaveable { mutableStateOf(0) }
var finPartida by rememberSaveable { mutableStateOf(false) }

-----------------------  posible función de victoria --------------------------------------------
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

    fun apostar(jug: Int){
        if(jug == 1){
            _jugador1.value?.apuesta()

        }
    }
 */