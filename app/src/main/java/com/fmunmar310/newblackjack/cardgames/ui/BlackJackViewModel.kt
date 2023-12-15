package com.fmunmar310.newblackjack.cardgames.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmunmar310.newblackjack.cardgames.data.Baraja
import com.fmunmar310.newblackjack.cardgames.data.Carta

/*
Tras mucho darle vueltas he decidido que la mejor opción era hacer sólo una vewModel que gestionara
dos vistas distintas ya que tenían muchos métodos en común y me ha parecido lo más práctico
la partida contra la IA funciona pulsando el botón de "Turno de la Máquina"
 */

/**
 * @property context indica el contexto de la app
 * @property _puntos1 almacena los puntos del jugador 1
 * @property puntos1 muestra los puntos del jugador 1
 * @property _puntos2 almacena los puntos del jugador 2
 * @property puntos2 muestra los puntos del jugador 2
 * @property _mano1 almacena la lista de cartas del jugador 1
 * @property mano1 muestra la lista de cartas del jugador 1
 * @property _mano2 almacena la lista de cartas del jugador 2
 * @property mano2 muestra la lista de cartas del jugador 2
 * @property _nombre1 almacena el nombre del jugador 1
 * @property nombre1 muestra el nombre del jugador 1
 * @property _nombre2 almacena el nombre del jugador 2
 * @property nombre2 muestra el nombre del jugador 2
 * @property _nombreEditado1 almacena un booleano que indica si el nombre del jugador 1 ha sido editado
 * @property nombreEditado1 muestra si el nombre del jugador 1 ha sido editado
 * @property _nombreEditado2 almacena un booleano que indica si el nombre del jugador 2 ha sido editado
 * @property nombreEditado2 muestra si el nombre del jugador 2 ha sido editado
 * @property _plantado1 almacena un booleano que indica si el jugador 1 se ha plantado
 * @property plantado1 muestra si el jugador 1 se ha plantado
 * @property _plantado2 almacena un booleano que indica si el jugador 2 se ha plantado
 * @property plantado2 muestra si el jugador 2 se ha plantado
 * @property _ganador almacena un Int que indica el ganador de la partida
 * @property ganador muestra el ganador de la partida
 * @property miBaraja inicializa una baraja de cartas
 * @property _barajaSize almacena el número de cartas que quedan en la baraja
 * @property barajaSize muestra el número de cartas que quedan en la baraja
 * @property _restart almacena un Int que se usa para forzar la actualización de la pantalla
 * @property restart muestra el valor de "_restart"
 * @property _turno almacena el jugador que está activo
 * @property turno muestra el jugador activo
 */

class BlackJackViewModel (application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    protected val context = getApplication<Application>().applicationContext

    protected val _puntos1  = MutableLiveData<Int>()
    val puntos1 : LiveData<Int> = _puntos1

    protected val _mano1 = MutableLiveData<MutableList<Carta>>()
    val mano1 : LiveData<MutableList<Carta>> = _mano1

    protected val _puntos2  = MutableLiveData<Int>()
    val puntos2 : LiveData<Int> = _puntos2

    protected val _mano2 = MutableLiveData<MutableList<Carta>>()
    val mano2 : LiveData<MutableList<Carta>> = _mano2

    protected val _nombre1 = MutableLiveData<String>()
    val nombre1: LiveData<String> = _nombre1

    protected val _nombreEditado1 = MutableLiveData<Boolean>()
    val nombreEditado1: LiveData<Boolean> = _nombreEditado1

    protected val _nombreEditado2 = MutableLiveData<Boolean>()
    val nombreEditado2: LiveData<Boolean> = _nombreEditado2

    protected val _nombre2 = MutableLiveData<String>()
    val nombre2: LiveData<String> = _nombre2

    protected val _plantado1 = MutableLiveData<Boolean>()
    val plantado1: LiveData<Boolean> = _plantado1

    protected val _plantado2 = MutableLiveData<Boolean>()
    val plantado2: LiveData<Boolean> = _plantado2

    protected val _ganador = MutableLiveData<Int>()
    val ganador : LiveData<Int> = _ganador

    protected val miBaraja = Baraja

    //Se utiliza para forzar la actualización de la pantalla
    protected  val _barajaSize = MutableLiveData<Int>()
    val barajaSize : LiveData<Int> = _barajaSize

    //Se utiliza para forzar la actualización de la pantalla
    protected val _restart = MutableLiveData<Int>()
    val restart : LiveData<Int> = _restart

    protected val _turno = MutableLiveData<Int>()
    val turno : LiveData<Int> = _turno


    // Inicializamos los valores necesarios para el funcionamiento del juego
    init {
        _restart.value = 0
        restart()
        _mano1.value = mutableListOf()
        _mano2.value = mutableListOf()
        _nombreEditado1.value = false
        _nombreEditado2.value = false
    }

    /**
     * Funcion que se usa para cambiar el nombre de un jugador
     * @param nuevoNombre indica el nuevo nombre del jugador
     * @param num indica el jugador al que se le va a cambiar el nombre
     */
    fun cambiaNombre(nuevoNombre: String, num: Int){
        if(num == 1){
            //Cambiamos el nombre del jugador
            _nombre1.value = nuevoNombre
            //Actualizamos el valor de _nombreEditado1 a true
            _nombreEditado1.value = true
        }else if(num == 2){
            //Cambiamos el nombre del jugador
            _nombre2.value = nuevoNombre
            //Actualizamos el valor de _nombreEditado2 a true
            _nombreEditado2.value = true
        }
    }

    /**
     * Función que permite que se pueda editar de nuevo el nombre del jugador,
     * se invoca pulsando sobre el nombre de cada jugador
     */
    fun falseaNombreEditado(num: Int){
        if(num == 1){
            _nombreEditado1.value = false
        }else if (num == 2){
            _nombreEditado2.value = false
        }
    }

    /**
     * Función para añadir una carta a la mano del jugador
     * @param jug indica a la mano de qué jugador se añade la carta
     */
    fun dameCarta(jug: Int){
        val nuevaCarta = miBaraja.cogerCarta()
        if(jug == 1 && _turno.value == 1) {
            // Si el jugador no está plantado se añade la carta a su mano
            if (!_plantado1.value!!) {
                _mano1.value?.add(nuevaCarta!!)
                //Se calculan los puntos del jugador con la carta nueva añadida a su mano
                _puntos1.value = calculaPuntos(_mano1.value!!)
                cambiaTurno(1)
            } else {
                //Si el jugador está plantado se manda un mensaje de error
                toasted("El jugador está plantado")
            }
        }else if(jug == 2 && _turno.value == 2){
            // Si el jugador no está plantado se añade la carta a su mano
            if (!_plantado2.value!!) {
                _mano2.value?.add(nuevaCarta!!)
                //Se calculan los puntos del jugador con la carta nueva añadida a su mano
                _puntos2.value = calculaPuntos(_mano2.value!!)
                cambiaTurno(2)
            } else {
                //Si el jugador está plantado se manda un mensaje de error
                toasted("El jugador está plantado")
            }
        }else{
            toasted("Es el turno del otro jugador")
        }
        //Se actualiza el número de cartas restantes en la baraja
        _barajaSize.value = miBaraja.size
    }

    /**
     * Función que cambia el turno entre jugadores
     */
    fun cambiaTurno(turno: Int){
        when (turno){
            1-> _turno.value = if (!_plantado2.value!!) 2 else  1
            2-> _turno.value = if (!_plantado1.value!!) 1 else  2
        }
        sumaRestart()
    }

    /**
     * Función para obtener los puntos de un jugador
     * @param jug indica el jugador del cual queremos saber los puntos que tiene
     * @return devuelve un Int que indica los puntos que tiene un jugador concreto
     */
    fun getPuntos(jug: Int): Int {
        if(jug == 1){
            return calculaPuntos(_mano1.value!!)
        }else if(jug == 2){
            return calculaPuntos(_mano2.value!!)
        }
        return 0
    }

    /**
     * Función para plantar a un jugador
     * @param jug indica el jugador al que queremos plantar
     */
    fun plantarse(jug: Int, turno: Int){
        if(jug == 1 && turno == jug){
            if(!_plantado1.value!!){
                _plantado1.value = true
                _turno.value = 2
            }else{
                toasted("El jugador ya se ha plantado")
            }
        }else if(jug == 2 && turno == jug){
            if(!_plantado2.value!!){
                _plantado2.value = true
                _turno.value = 1
            }else{
                toasted("El jugador ya se ha plantado")
            }
        }else{
            toasted("Es el turno del otro jugador")
        }
    }

    /**
     * Función que actualiza el valor de _restart, se usa para forzar la actualización de la pantalla
     */
    fun sumaRestart(){
        _restart.value = _restart.value!! + 1
    }

    /**
     * Función que reinicia los valores del juego para poder jugar otra partida
     */
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
        _turno.value = 1
    }

    /**
     * Función que calcula los puntos de una lista de cartas
     * @param mano indica la lista de cartas
     * @return devuelve el total de puntos de dicha lista de cartas
     */
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

    /**
     * Función para mostrar un mensaje de error
     */
    fun toasted(mensaje: String){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    /**
     * Función para calcular quién ha ganado la partida
     * 1 -> jugador 1 gana
     * 2 -> jugador 2 gana
     * 3-> Empate
     */
    fun winBet(puntos1: Int, puntos2: Int, plantado1: Boolean, plantado2: Boolean){
        if(puntos1 == 21) {
            _ganador.value = 1
        }else if(puntos2 == 21){
            _ganador.value = 2
        }else if(puntos1 > 21 && puntos2 <= 21){
            _ganador.value = 2
        }else if(puntos2 > 21 && puntos1 <= 21){
           _ganador.value = 1
        }else if((puntos1 < 21 && plantado1) && (puntos2 < puntos1 && plantado2)){
           _ganador.value = 1
        }else if((puntos2 < 21 && plantado2) && (puntos1 < puntos2 && plantado1)) {
           _ganador.value = 2
        }else if ((plantado1 && plantado2) && (puntos1 == puntos2)){
            _ganador.value = 3
        }else{
            _ganador.value = 0
        }
    }

    /**
     * Función para cambiar el nombre en la versión vs la máquina
     */
    fun cambiaNombreMk(nuevoNombre: String, num: Int){
        if(num == 1){
            //Cambiamos el nombre del jugador
            _nombre1.value = nuevoNombre
            //Actualizamos el valor de _nombreEditado1 a true
            _nombreEditado1.value = true
        }else if(num == 2){
            //Cambiamos el nombre del jugador
            _nombre2.value = "Skynet"
            //Actualizamos el valor de _nombreEditado2 a true
            _nombreEditado2.value = true
        }
    }

    /**
     * Función que actua como IA de la máquina
     */
    fun juegaMaquina(){
        if(_puntos2.value!! < 18){
            dameCarta(2)
            winBet(_puntos1.value!!,_puntos2.value!!,_plantado1.value!!,_plantado2.value!!)
        }else if(_puntos2.value == 18){
            plantarse(2,2)
            winBet(_puntos1.value!!,_puntos2.value!!,_plantado1.value!!,_plantado2.value!!)
        }
        sumaRestart()
    }
}
