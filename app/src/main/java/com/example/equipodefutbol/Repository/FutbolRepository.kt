package com.example.equipodefutbol.repository

import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import com.example.equipodefutbol.service.ApiService

class FutbolRepository(private val apiService: ApiService) {

    // Consulta 1: Jugadores por equipo
    suspend fun obtenerJugadoresDeEquipo(id: Int): List<Jugador> {
        return apiService.getJugadoresPorEquipo(id)
    }

    // Consulta 2: Goleadores con filtro
    suspend fun obtenerGoleadores(minimo: Int): List<Jugador> {
        return apiService.getTopGoleadores(minimo)
    }

    // Consulta 3: Resultados con nombres de equipos
    suspend fun obtenerResultadosPartidos(): List<PartidoDetalle> {
        return apiService.getResultadosPartidos()
    }
}