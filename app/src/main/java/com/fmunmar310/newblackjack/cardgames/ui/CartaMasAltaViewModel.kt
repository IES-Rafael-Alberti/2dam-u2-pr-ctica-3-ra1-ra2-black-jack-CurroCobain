package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmunmar310.newblackjack.cardgames.data.Baraja
import com.fmunmar310.newblackjack.cardgames.data.Carta

/**
 * @property context indica el contexto de la app
 * @property _imageId indica un texto que se usa para crear el idDrawable de la carta
 * @property imageId muestra públicamente el valor de "_imageId"
 * @property miBaraja inicializa una baraja de cartas
 */
class CartaMasAltaViewModel (application: Application): AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val _imageId = MutableLiveData<String>()
    val imageId : LiveData<String> = _imageId
    private val miBaraja = Baraja

    // Llamamos a la función restart() en el init de nuestra viewModel
    init {
        restart()
    }

    /**
     * Función que saca una carta de la baraja y modifica el valor de _imageId
     * @see Baraja
     */
    fun dameCartaId(){
        // Llamamos a la función cogerCarta() de Baraja para obtener una carta
        val nuevaCarta = miBaraja.cogerCarta()
        //Si quedan cartas en la baraja se actualiza el valor de _imageID
        if (miBaraja.size > 0){
            _imageId.value = nuevaCarta?.idDrawable.toString()
        // Si no quedan cartas en la baraja se manda un mensaje de error
        }else{
            Toast.makeText(context, "No quedan cartas en la baraja", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Función que reinicia la baraja y actualiza el valor de _imageId a su valor por defecto.
     */
    fun restart(){
        miBaraja.reiniciar(context)
        _imageId.value = "_reverso"
    }

    /**
     * Función par obtener el número de cartas restantes de la baraja
     * @return Devuelve un int que indica el tamaño actual de la baraja
     */
    fun restoDeCartas() = miBaraja.size
}