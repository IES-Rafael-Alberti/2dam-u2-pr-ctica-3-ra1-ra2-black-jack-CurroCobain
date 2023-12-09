package com.fmunmar310.newblackjack.cardgames.data

/**
 * Clase Jugador que se usa en el juego BlackJack
 * @property _nombre representa el nombre del jugador
 * @property nombre muestra públicamente el nombre del jugador
 * @property _mano representa la mano de cartas que tiene el jugador
 * @property mano muestra públicamente la mano actual del jugador
 * @property _plantado indica si el jugador está plantado en la mano actual
 * @property plantado muestra públicamente si el jugador está plantado en la mano actual
 */
class Jugador(
    private var _nombre: String,
    private var _mano: MutableList<Carta>
    ) {
    private var _plantado: Boolean = false
    val plantado : Boolean = _plantado
    val nombre: String = _nombre
    val mano : MutableList<Carta> =_mano

    // Iniciamos la partida con 5 fichas
    init {
        this._nombre = "jugador"
        this._mano = mutableListOf()
        this._plantado = false
    }

    /**
     * Actualiza el nombre del jugador
     * @param nomb nombre del jugador recibido por teclado
     */
    fun cambiaNombre(nomb: String){
        this._nombre = nomb
    }
    /**
     * Añade una carta a la mano del jugador
     */
    fun addCarta(carta: Carta) {
        this._mano.add(carta)
    }
    /**
     * Calcula el total de puntos de la mano actual del jugador
     * @return devuelve un Int con el total de puntos
     * @param puntos indica el total de puntos de la mano del jugador
     * @see Carta
     */
    fun calculaPuntos(): Int {
        var puntos = 0
        if (_mano.isNotEmpty()) {
            for (i in this._mano) {
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
     * Planta al jugador en la mano actual
     */
    fun plantarse(){
        this._plantado = true
    }

    /**
     * Devuelve los valores a su estado inicial
     */
    fun reiniciaPartida(){
        this._plantado = false
        this._mano.clear()
        this._nombre = "jugador"
    }
}

// Esto no se usa de momento, tengo que cambiar y usar jugador pa algo, o no....


