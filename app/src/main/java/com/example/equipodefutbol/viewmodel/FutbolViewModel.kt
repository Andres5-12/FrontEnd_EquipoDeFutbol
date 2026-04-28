package com.example.equipodefutbol.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodefutbol.model.Equipo
import com.example.equipodefutbol.model.Jugador
import com.example.equipodefutbol.model.PartidoDetalle
import com.example.equipodefutbol.repository.FutbolRepository
import kotlinx.coroutines.launch

class FutbolViewModel(private val repository: FutbolRepository) : ViewModel() {

    // Estado para la lista de jugadores de un equipo
    private val _jugadoresEquipo = mutableStateOf<List<Jugador>>(emptyList())
    val jugadoresEquipo: State<List<Jugador>> = _jugadoresEquipo

    // Estado para la consulta de goleadores
    private val _goleadores = mutableStateOf<List<Jugador>>(emptyList())
    val goleadores: State<List<Jugador>> = _goleadores

    // Estado para la lista de partidos
    private val _partidos = mutableStateOf<List<PartidoDetalle>>(emptyList())
    val partidos: State<List<PartidoDetalle>> = _partidos

    init {
        cargarJugadoresPorEquipo(1) // Carga inicial de jugadores
        cargarPartidos()            // <--- AGREGA ESTA LÍNEA AQUÍ
    }

    // Función para cargar jugadores (Consulta 1)
    fun cargarJugadoresPorEquipo(id: Int) {
        viewModelScope.launch {
            try {
                _jugadoresEquipo.value = repository.obtenerJugadoresDeEquipo(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Función para cargar goleadores (Consulta 2)
    fun cargarGoleadores(minimo: Int) {
        viewModelScope.launch {
            try {
                _goleadores.value = repository.obtenerGoleadores(minimo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Función para cargar todos los partidos (Consulta 3)
    fun cargarPartidos() {
        viewModelScope.launch {
            try {
                _partidos.value = repository.obtenerResultadosPartidos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun borrarEquipo(id: Int) {
        viewModelScope.launch {
            try {
                repository.borrarEquipo(id)
                _jugadoresEquipo.value = emptyList()
                cargarPartidos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun crearEquipo(nuevoEquipo: Equipo) {
        viewModelScope.launch {
            try {
                repository.insertarEquipo(nuevoEquipo)
                                cargarPartidos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}