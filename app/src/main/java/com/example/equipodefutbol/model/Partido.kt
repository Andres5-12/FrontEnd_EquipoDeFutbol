package com.example.equipodefutbol.model

import com.google.gson.annotations.SerializedName

data class Partido(
    @SerializedName("id_partido") val idPartido: Int,
    val fecha: String,
    val estadio: String,
    @SerializedName("equipo_local") val equipoLocal: Int,
    @SerializedName("equipo_visita") val equipoVisita: Int,
    @SerializedName("goles_local") val golesLocal: Int,
    @SerializedName("goles_visita") val golesVisita: Int
)