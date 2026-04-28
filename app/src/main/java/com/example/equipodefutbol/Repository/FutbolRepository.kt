package com.example.equipodefutbol.repository

import com.example.equipodefutbol.model.Equipo
import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import com.example.equipodefutbol.service.ApiService

class FutbolRepository(private val apiService: ApiService) {

    suspend fun obtenerJugadoresDeEquipo(equipoId: Int): List<Jugador> {
        return apiService.getJugadoresPorEquipo(equipoId)
    }

    suspend fun obtenerGoleadores(minGoles: Int): List<Jugador> {
        return apiService.getTopGoleadores(minGoles)
    }

    suspend fun obtenerResultadosPartidos(): List<PartidoDetalle> {
        return apiService.getResultadosPartidos()
    }

    suspend fun borrarEquipo(equipoId: Int) {
        apiService.deleteEquipo(equipoId)
    }

    suspend fun insertarEquipo(equipo: Equipo) {
        apiService.postEquipo(equipo)
    }
}
