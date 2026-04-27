package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class Jugador(
    @SerializedName("id_jugador") val idJugador: Int,
    val nombre: String,
    val posicion: String,
    val dorsal: Int,
    @SerializedName("fecha_nac") val fechaNac: String,
    val nacionalidad: String,
    @SerializedName("id_equipo") val idEquipo: Int
)