package com.example.equipodefutbol.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(viewModel: FutbolViewModel) {
    val equipos by viewModel.listaEquipos

    // Cargamos los datos al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarTodosLosEquipos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Directorio de Clubes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        if (equipos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(equipos) { equipo ->
                    EquipoInfoCard(
                        nombre = equipo.nombre,
                        entrenador = "Xavi Hernández", // O equipo.entrenador si tu modelo lo tiene
                        ciudad = equipo.ciudad,
                        fundacion = equipo.fundacion
                    )
                }
            }
        }
    }
}

@Composable
fun EquipoInfoCard(nombre: String, entrenador: String, ciudad: String, fundacion: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre del Equipo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = nombre.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            // Información Detallada
            InfoRow(icon = Icons.Default.Person, label = "Entrenador", value = entrenador)
            InfoRow(icon = Icons.Default.LocationOn, label = "Ciudad", value = ciudad)

            Text(
                text = "Desde $fundacion",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}