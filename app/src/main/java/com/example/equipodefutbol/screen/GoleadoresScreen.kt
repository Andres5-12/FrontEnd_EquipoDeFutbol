package com.example.equipodefutbol.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@Composable
fun GoleadoresScreen(viewModel: FutbolViewModel) {
    // Estado para el input de búsqueda
    var minGolesInput by remember { mutableStateOf("5") }
    val goleadores = viewModel.goleadores.value

    // Carga inicial al abrir la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarGoleadores(5)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // --- CABECERA Y FILTROS ---
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tabla de Goleadores",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda y botón "Ver Todo"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = minGolesInput,
                    onValueChange = { minGolesInput = it },
                    label = { Text("Mín. Goles") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    val goles = minGolesInput.toIntOrNull() ?: 0
                    viewModel.cargarGoleadores(goles)
                }) {
                    Text("Buscar")
                }
            }

            TextButton(
                onClick = {
                    minGolesInput = "0"
                    viewModel.cargarGoleadores(0)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ver todos los goleadores")
            }
        }

        // --- LISTA DE RESULTADOS ---
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(goleadores) { index, jugador ->
                GoleadorCard(
                    nombre = jugador.nombre,
                    posicion = jugador.posicion,
                    goles = jugador.goles,
                    ranking = index + 1
                )
            }
        }
    }
}

@Composable
fun GoleadorCard(nombre: String, posicion: String, goles: Int?, ranking: Int) {
    val badgeColor = when (ranking) {
        1 -> MaterialTheme.colorScheme.primary // Oro
        2 -> MaterialTheme.colorScheme.secondary // Plata
        3 -> MaterialTheme.colorScheme.tertiary // Bronce
        else -> MaterialTheme.colorScheme.outline
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (ranking <= 3) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (ranking <= 3) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ranking Badge
            Surface(
                shape = CircleShape,
                color = badgeColor,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = ranking.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = posicion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Contador de Goles
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = goles?.toString() ?: "0",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "GOLES",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}