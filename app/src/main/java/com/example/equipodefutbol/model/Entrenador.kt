package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class Entrenador(
    @SerializedName("id_entrenador") val idEntrenador: Int,
    val nombre: String,
    val especialidad: String,
    @SerializedName("id_equipo") val idEquipo: Int
)