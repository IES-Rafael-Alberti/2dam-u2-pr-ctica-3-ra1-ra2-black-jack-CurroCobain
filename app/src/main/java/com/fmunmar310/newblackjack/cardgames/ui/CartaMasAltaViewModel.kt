package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmunmar310.newblackjack.cardgames.data.Baraja
import com.fmunmar310.newblackjack.cardgames.data.Carta

class CartaMasAltaViewModel (application: Application): AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val _imagedId = MutableLiveData<String>()
    val imageId : LiveData<String> = _imagedId
    private val _miCarta = MutableLiveData<Carta>()
    private val miBaraja = Baraja
    init {
        restart()
    }
    fun dameCartaId(){
        val nuevaCarta = miBaraja.cogerCarta()
        if (miBaraja.size > 0){
            _miCarta.value = nuevaCarta
            _imagedId.value = _miCarta.value?.idDrawable.toString()
        }else{
            Toast.makeText(context, "No quedan cartas en la baraja", Toast.LENGTH_SHORT).show()
        }
    }
    fun restart(){
        miBaraja.reiniciar(context)
        _imagedId.value = "_reverso"
        _miCarta.value = null
    }
    fun restoDeCartas() = miBaraja.size


}