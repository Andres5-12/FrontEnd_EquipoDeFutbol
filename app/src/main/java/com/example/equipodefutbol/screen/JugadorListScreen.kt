package com.example.equipodefutbol.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@Composable
fun JugadorListScreen(viewModel: FutbolViewModel, equipoId: Int) {
    val jugadores = viewModel.jugadoresEquipo.value

    LaunchedEffect(equipoId) {
        viewModel.cargarJugadoresPorEquipo(equipoId)
    }

    // Eliminamos el padding interno de aquí para que las tarjetas respiren mejor
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp) // Espacio al final de la lista
    ) {
        item {
            Text(
                text = "Plantilla del Equipo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(jugadores) { jugador ->
            // AQUÍ LLAMAMOS A LA FUNCIÓN PULIDA
            JugadorCard(
                nombre = jugador.nombre,
                posicion = jugador.posicion,
                dorsal = jugador.dorsal
            )
        }
    }
}

// ESTA FUNCIÓN VA AFUERA, como una función independiente
@Composable
fun JugadorCard(nombre: String, posicion: String, dorsal: Int?, goles: Int? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con el número
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dorsal?.toString() ?: "-",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = posicion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (goles != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = goles.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Goles",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}