package com.example.equipodefutbol.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@Composable
fun JugadorListScreen(viewModel: FutbolViewModel, equipoId: Int) {
    val jugadores = viewModel.jugadoresEquipo.value

    // Se ejecuta una sola vez al abrir la pantalla
    LaunchedEffect(equipoId) {
        viewModel.cargarJugadoresPorEquipo(equipoId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Plantilla del Equipo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(jugadores) { jugador ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "${jugador.dorsal} - ${jugador.nombre}", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Posición: ${jugador.posicion}")
                        Text(text = "Nacionalidad: ${jugador.nacionalidad}")
                    }
                }
            }
        }
    }
}