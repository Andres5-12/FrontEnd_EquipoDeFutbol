package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class Equipo(
    @SerializedName("id_equipo") val idEquipo: Int,
    val nombre: String,
    val ciudad: String,
    val fundacion: String // Lo recibimos como String (ej. "1946-06-18") para facilitar la lectura
)