package com.example.equipodefutbol.model

data class PartidoDetalle(
    val idPartido: Long?,
    val fecha: String?,
    val estadio: String?,
    val equipoLocal: String,
    val equipoVisita: String,
    val golesLocal: Int,
    val golesVisita: Int
)