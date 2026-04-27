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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@Composable
fun PartidosScreen(viewModel: FutbolViewModel) {
    // Nota: Deberás agregar la variable 'partidos' a tu ViewModel
    val partidos = viewModel.partidos.value

    LaunchedEffect(Unit) {
        viewModel.cargarPartidos()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Resultados Oficiales",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(partidos) { partido ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Estadio: ${partido.estadio} | Fecha: ${partido.fecha}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Diseño del marcador: Local vs Visita
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = partido.equipoLocal, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text(
                                text = "${partido.golesLocal} - ${partido.golesVisita}",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(text = partido.equipoVisita, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}