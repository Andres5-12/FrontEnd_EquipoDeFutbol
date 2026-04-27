package com.example.equipodefutbol.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@Composable
fun PartidosScreen(viewModel: FutbolViewModel) {
    val partidos = viewModel.partidos.value

    LaunchedEffect(Unit) {
        viewModel.cargarPartidos()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "Resultados Oficiales",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(partidos) { partido ->
            // Inyectamos la nueva tarjeta pulida aquí
            PartidoCard(
                equipoLocal = partido.equipoLocal,
                equipoVisita = partido.equipoVisita,
                golesLocal = partido.golesLocal,
                golesVisita = partido.golesVisita,
                fecha = partido.fecha,
                estadio = partido.estadio
            )
        }
    }
}

@Composable
fun PartidoCard(
    equipoLocal: String,
    equipoVisita: String,
    golesLocal: Int,
    golesVisita: Int,
    fecha: String,
    estadio: String
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fecha y Estadio en la parte superior
            Text(
                text = "$fecha • $estadio",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Diseño del Marcador
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Equipo Local
                Text(
                    text = equipoLocal,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Caja del Resultado (Score)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "$golesLocal - $golesVisita",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                // Equipo Visita
                Text(
                    text = equipoVisita,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}