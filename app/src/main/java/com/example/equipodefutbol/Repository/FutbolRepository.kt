package com.example.equipodefutbol.repository

import com.example.equipodefutbol.model.Equipo
import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import com.example.equipodefutbol.service.ApiService

class FutbolRepository(private val apiService: ApiService) {

    suspend fun obtenerJugadoresDeEquipo(id: Int): List<Jugador> {
        return apiService.getJugadoresPorEquipo(id)
    }

    suspend fun obtenerGoleadores(Goles: Int): List<Jugador> {
        return apiService.getTopGoleadores(Goles)
    }

    suspend fun obtenerResultadosPartidos(): List<PartidoDetalle> {
        return apiService.getResultadosPartidos()
    }

    suspend fun borrarEquipo(Id: Int) {
        apiService.deleteEquipo(Id)
    }

    suspend fun insertarEquipo(equipo: Equipo) {
        apiService.postEquipo(equipo)
    }

    suspend fun obtenerEquipos(): List<Equipo> {
        return apiService.getEquipos()
    }
}
