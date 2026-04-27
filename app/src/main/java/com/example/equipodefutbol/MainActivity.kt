package com.example.equipodefutbol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.equipodefutbol.repository.FutbolRepository
import com.example.equipodefutbol.screen.GoleadoresScreen
import com.example.equipodefutbol.screen.JugadorListScreen
import com.example.equipodefutbol.screen.PartidosScreen
import com.example.equipodefutbol.service.RetrofitClient
import com.example.equipodefutbol.ui.theme.EquipodeFutbolTheme
import com.example.equipodefutbol.viewmodel.FutbolViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Lógica de inicialización (FUERA del setContent)
        val apiService = RetrofitClient.apiService
        val repository = FutbolRepository(apiService)
        val viewModel = FutbolViewModel(repository)

        setContent {
            // Usamos el nombre exacto que encontramos en tu Theme.kt
            EquipodeFutbolTheme {
                var currentScreen by remember { mutableStateOf("jugadores") }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentScreen == "jugadores",
                                onClick = { currentScreen = "jugadores" },
                                label = { Text("Plantilla") },
                                icon = { Icon(Icons.Default.Person, contentDescription = null) }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "goleadores",
                                onClick = { currentScreen = "goleadores" },
                                label = { Text("Goleadores") },
                                icon = { Icon(Icons.Default.Info, contentDescription = null) }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "partidos",
                                onClick = { currentScreen = "partidos" },
                                label = { Text("Resultados") },
                                icon = { Icon(Icons.Default.List, contentDescription = null) }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            "jugadores" -> JugadorListScreen(viewModel = viewModel, equipoId = 1)
                            "goleadores" -> GoleadoresScreen(viewModel = viewModel, minGoles = 5)
                            "partidos" -> PartidosScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}