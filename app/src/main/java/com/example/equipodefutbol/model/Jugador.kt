package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class Jugador(
    @SerializedName("idJugador") val id: Int,
    val nombre: String,
    val posicion: String,
    val dorsal: Int?,
    // En el navegador dice 'equipoNombre', no 'nacionalidad'
    @SerializedName("equipoNombre") val nacionalidad: String?,
    val goles: Int? = 0
)