package com.example.equipodefutbol.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodefutbol.model.Equipo
import com.example.equipodefutbol.model.Jugador
// REMOVIDO: El import de PartidoDetalle ya no se usa, esto quita el "Unused import directive"
import com.example.equipodefutbol.repository.FutbolRepository
import kotlinx.coroutines.launch

class FutbolViewModel(private val repository: FutbolRepository) : ViewModel() {

    // --- ESTADOS ---

    private val _jugadoresEquipo = mutableStateOf<List<Jugador>>(emptyList())
    val jugadoresEquipo: State<List<Jugador>> = _jugadoresEquipo

    private val _equipoSeleccionado = mutableStateOf<Equipo?>(null)
    val equipoSeleccionado: State<Equipo?> = _equipoSeleccionado

    private val _goleadores = mutableStateOf<List<Jugador>>(emptyList())
    val goleadores: State<List<Jugador>> = _goleadores

    private val _listaEquipos = mutableStateOf<List<Equipo>>(emptyList())
    val listaEquipos: State<List<Equipo>> = _listaEquipos

    // --- FUNCIONES ---

    fun cargarTodosLosEquipos() {
        viewModelScope.launch {
            try {
                val respuesta = repository.obtenerEquipos()
                _listaEquipos.value = respuesta
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al cargar lista de equipos: ${e.message}")
            }
        }
    }

    fun cargarJugadoresPorEquipo(id: Int) {
        viewModelScope.launch {
            try {
                val resultadoJugadores = repository.obtenerJugadoresDeEquipo(id)
                _jugadoresEquipo.value = resultadoJugadores

                val todosLosEquipos = repository.obtenerEquipos()
                _equipoSeleccionado.value = todosLosEquipos.find { it.idEquipo == id }
            } catch (e: Exception) {
                Log.e("DEBUG_DATA", "Error en la petición: ${e.message}")
            }
        }
    }

    fun limpiarListaJugadores() {
        _jugadoresEquipo.value = emptyList()
        _equipoSeleccionado.value = null
    }

    fun cargarGoleadores(minimo: Int) {
        viewModelScope.launch {
            try {
                _goleadores.value = repository.obtenerGoleadores(minimo)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
            }
        }
    }

    fun borrarEquipo(id: Int) {
        viewModelScope.launch {
            try {
                repository.borrarEquipo(id)
                cargarTodosLosEquipos()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al borrar: ${e.message}")
            }
        }
    }

    /**
     * CORRECCIÓN: fundacion ahora es Int para evitar 'Argument type mismatch'
     */
    fun crearEquipoAutomatico(nombre: String, ciudad: String, fundacion: String) {
        viewModelScope.launch {
            try {
                val equiposExistentes = repository.obtenerEquipos()
                val idsUsados = equiposExistentes.map { it.idEquipo }.toSet()

                var nuevoId = 1
                while (idsUsados.contains(nuevoId)) {
                    nuevoId++
                }

                // El constructor de Equipo espera un Int en fundacion
                val equipoConId = Equipo(
                    idEquipo = nuevoId,
                    nombre = nombre,
                    ciudad = ciudad,
                    fundacion = fundacion
                )

                repository.insertarEquipo(equipoConId)
                cargarTodosLosEquipos()

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al crear: ${e.message}")
            }
        }
    }
}