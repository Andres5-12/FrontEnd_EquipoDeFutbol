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
fun GoleadoresScreen(viewModel: FutbolViewModel, minGoles: Int) {
        val goleadores = viewModel.goleadores.value

    LaunchedEffect(minGoles) {
        viewModel.cargarGoleadores(minGoles)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Goleadores (+ de $minGoles goles)",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(goleadores) { jugador ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = jugador.nombre, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Posición: ${jugador.posicion}")
                    }
                }
            }
        }
    }
}