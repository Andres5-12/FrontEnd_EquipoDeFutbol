package com.example.equipodefutbol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.equipodefutbol.repository.FutbolRepository
import com.example.equipodefutbol.screen.EquiposScreen
import com.example.equipodefutbol.screen.GoleadoresScreen
import com.example.equipodefutbol.screen.JugadorListScreen
import com.example.equipodefutbol.service.RetrofitClient
import com.example.equipodefutbol.ui.theme.EquipodeFutbolTheme
import com.example.equipodefutbol.viewmodel.FutbolViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicialización de la arquitectura (Model -> Repository -> ViewModel)
        val apiService = RetrofitClient.apiService
        val repository = FutbolRepository(apiService)
        val viewModel = FutbolViewModel(repository)

        setContent {
            EquipodeFutbolTheme {
                // Estado para controlar la navegación entre pantallas
                var currentScreen by remember { mutableStateOf("clubes") }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            // Opción 1: Directorio de Clubes (Nueva)
                            NavigationBarItem(
                                selected = currentScreen == "clubes",
                                onClick = { currentScreen = "clubes" },
                                label = { Text("Clubes") },
                                icon = { Icon(Icons.Default.Home, contentDescription = null) }
                            )

                            // Opción 2: Plantilla (Consulta por ID)
                            NavigationBarItem(
                                selected = currentScreen == "jugadores",
                                onClick = { currentScreen = "jugadores" },
                                label = { Text("Plantilla") },
                                icon = { Icon(Icons.Default.Person, contentDescription = null) }
                            )

                            // Opción 3: Goleadores
                            NavigationBarItem(
                                selected = currentScreen == "goleadores",
                                onClick = { currentScreen = "goleadores" },
                                label = { Text("Goleadores") },
                                icon = { Icon(Icons.Default.Star, contentDescription = null) }
                            )
                        }
                    }
                ) { innerPadding ->
                    // Contenedor principal que reacciona al cambio de pantalla
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            "clubes" -> {
                                EquiposScreen(viewModel = viewModel)
                            }
                            "jugadores" -> {
                                // Mantenemos el ID inicial como entero (Int) para evitar errores de tipo
                                JugadorListScreen(
                                    viewModel = viewModel,
                                    equipoIdInicial = 2
                                )
                            }
                            "goleadores" -> {
                                GoleadoresScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}