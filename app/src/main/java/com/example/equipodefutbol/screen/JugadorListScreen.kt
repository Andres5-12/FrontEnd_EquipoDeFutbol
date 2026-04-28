package com.example.equipodefutbol.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    if (jugadores.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Esto muestra el círculo de carga
        }
    } else {
    LaunchedEffect(equipoId) {
        viewModel.cargarJugadoresPorEquipo(equipoId)
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Aquí irá tu lógica de crear equipo */ },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo Equipo") },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Fila de Botones de Acción
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón Borrar
                Button(
                    onClick = {
                        // Llamamos a la función del ViewModel pasándole el ID del equipo actual
                        viewModel.borrarEquipo(equipoId)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Borrar Equipo")
                }

                // Botón Refrescar
                OutlinedButton(
                    onClick = {
                        // Volvemos a pedir los datos a la API
                        viewModel.cargarJugadoresPorEquipo(equipoId)
                    }
                ) {
                    Text("Refrescar")
                }
            }

            // Lista de Jugadores
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp) // Espacio para el FAB
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
                    JugadorCard(
                        nombre = jugador.nombre,
                        posicion = jugador.posicion,
                        dorsal = jugador.dorsal,
                        nacionalidad = jugador.nacionalidad
                    )
                }
                }
            }
        }
    }
}

@Composable
fun JugadorCard(
    nombre: String,
    posicion: String,
    dorsal: Int?,
    nacionalidad: String? = null,
    goles: Int? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
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
                Text(text = nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = posicion, style = MaterialTheme.typography.bodyMedium)
                if (nacionalidad != null) {
                    Text(text = nacionalidad, style = MaterialTheme.typography.labelSmall)
                }
            }

            if (goles != null) {
                Text(
                    text = "$goles G",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}