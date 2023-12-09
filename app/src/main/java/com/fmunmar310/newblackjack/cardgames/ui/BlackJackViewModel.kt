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
    private val _imagedId = MutableLiveData<String>()
    val imageId : LiveData<String> = _imagedId
    private val _puntos1  = MutableLiveData<Int>()
    val puntos1 : LiveData<Int> = _puntos1
    private val _mano1 = MutableLiveData<MutableList<Carta>>()
    val mano1 : LiveData<MutableList<Carta>> = _mano1
    private val _puntos2  = MutableLiveData<Int>()
    val puntos2 : LiveData<Int> = _puntos2
    private val _mano2 = MutableLiveData<MutableList<Carta>>()
    val mano2 : LiveData<MutableList<Carta>> = _mano2
    private val _miCarta = MutableLiveData<Carta>()
    private val _nombre1 = MutableLiveData<String>()
    val nombre1: LiveData<String> = _nombre1
    private val _nombre2 = MutableLiveData<String>()
    val nombre2: LiveData<String> = _nombre2
    private val _plantado1 = MutableLiveData<Boolean>()
    val plantado1: LiveData<Boolean> = _plantado1
    private val _plantado2 = MutableLiveData<Boolean>()
    val plantado2: LiveData<Boolean> = _plantado2
    val miCarta : MutableLiveData<Carta> = _miCarta
    private val miBaraja = Baraja
    private  val _barajaSize = MutableLiveData<Int>()
    val barajaSize : LiveData<Int> = _barajaSize

    init {
        restart()
        _mano1.value = mutableListOf()
        _mano2.value = mutableListOf()
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
    fun restart(){
        miBaraja.reiniciar(context)
        _mano1.value?.clear()
        _mano2.value?.clear()
        _puntos1.value = 0
        _puntos2.value = 0
        _plantado1.value = false
        _plantado2.value = false
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