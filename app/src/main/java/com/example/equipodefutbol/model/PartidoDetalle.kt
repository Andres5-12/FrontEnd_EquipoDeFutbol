package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class PartidoDetalle(
    @SerializedName("id_partido") val idPartido: Int,
    val fecha: String,
    val estadio: String,
    @SerializedName("equipo_local") val equipoLocal: String, // Aquí recibiremos el nombre, no el ID
    @SerializedName("equipo_visita") val equipoVisita: String, // Aquí recibiremos el nombre, no el ID
    @SerializedName("goles_local") val golesLocal: Int,
    @SerializedName("goles_visita") val golesVisita: Int
)