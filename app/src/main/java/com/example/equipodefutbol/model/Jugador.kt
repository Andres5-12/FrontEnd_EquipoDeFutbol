package com.example.equipodefutbol.model

data class Jugador(
    val nombre: String,
    val posicion: String,
    val dorsal: Int?,
    val fechaNacimiento: String?, // El backend envía fechaNacimiento [cite: 161]
    val nacionalidad: String?,
    val equipoNombre: String?, // El backend envía el nombre del equipo [cite: 162]
    val goles: Int? = null
)