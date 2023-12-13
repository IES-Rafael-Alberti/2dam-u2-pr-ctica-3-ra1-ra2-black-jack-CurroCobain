package com.fmunmar310.newblackjack.cardgames.data

import android.content.Context

/**
 * Clase baraja
 * @property listaCartas lista de cartas de la baraja
 * @property size indica la cantidad de cartas de la baraja
 * @see Carta
 */
class Baraja {
    companion object{
        val listaCartas : ArrayList<Carta> = arrayListOf()
        var size = 0

        /**
         * Crea la baraja de cartas desde las clases Naipes y Palos
         * @see Naipes
         * @see Palos
         */
        fun creaBaraja(context: Context){
            listaCartas.clear()
            for(i in Palos.values()){
                for(x in Naipes.values()){
                    val micarta = Carta(x,i,0,0,0)
                    listaCartas.add(micarta)
                }
            }
            size = listaCartas.size
        }

        /**
         * Función para barajar las cartas
         */
        fun barajar(){
            listaCartas.shuffle()
        }
        /**
         * Función que saca una carta de la baraja
         * @return devuelve una carta de la baraja
         */
        fun cogerCarta(): Carta?{
            if (listaCartas.size>0) {
                val cartaTemp = listaCartas.last()
                listaCartas.remove(cartaTemp)
                size = listaCartas.size
                return cartaTemp
            }
            return null
        }

        /**
         * Función para reiniciar la baraja
         */
        fun reiniciar(context: Context){
            creaBaraja(context)
            barajar()
        }
    }
}