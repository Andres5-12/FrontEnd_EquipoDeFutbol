package com.example.equipodefutbol.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.equipodefutbol.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorListScreen(viewModel: FutbolViewModel, equipoIdInicial: Int) {
    val jugadores by viewModel.jugadoresEquipo
    val equipoActual by viewModel.equipoSeleccionado // USADO: Para el encabezado

    var idTexto by remember { mutableStateOf(equipoIdInicial.toString()) }
    var mostrarDialogoCrear by remember { mutableStateOf(false) }
    var mostrarConfirmacionBorrar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarJugadoresPorEquipo(equipoIdInicial)
    }

    // --- DIÁLOGO PARA NUEVO EQUIPO ---
    if (mostrarDialogoCrear) {
        var campoNombre by remember { mutableStateOf("") }
        var campoCiudad by remember { mutableStateOf("") }
        var campoAnio by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { mostrarDialogoCrear = false },
            title = { Text("Registrar Nuevo Equipo") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = campoNombre, onValueChange = { campoNombre = it }, label = { Text("Nombre") })
                    OutlinedTextField(value = campoCiudad, onValueChange = { campoCiudad = it }, label = { Text("Ciudad") })
                    OutlinedTextField(
                        value = campoAnio,
                        onValueChange = { if (it.all { c -> c.isDigit() }) campoAnio = it },
                        label = { Text("Año de Fundación") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // CORRECCIÓN: Convertimos campoAnio a Int para evitar 'Argument type mismatch'
                    viewModel.crearEquipoAutomatico(
                        nombre = campoNombre,
                        ciudad = campoCiudad,
                        fundacion = campoAnio
                    )
                    mostrarDialogoCrear = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogoCrear = false }) { Text("Cancelar") } }
        )
    }

    // --- DIÁLOGO DE CONFIRMACIÓN PARA BORRAR ---
    if (mostrarConfirmacionBorrar) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionBorrar = false },
            title = { Text("¿Eliminar Equipo?") },
            text = { Text("Se eliminará el equipo con ID: $idTexto.") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = {
                        // USADO: Función borrarEquipo del ViewModel
                        idTexto.toIntOrNull()?.let { viewModel.borrarEquipo(it) }
                        mostrarConfirmacionBorrar = false
                    }
                ) { Text("Eliminar") }
            },
            dismissButton = { TextButton(onClick = { mostrarConfirmacionBorrar = false }) { Text("Cancelar") } }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Equipos") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { mostrarDialogoCrear = true }, // USADO: Lógica nuevo equipo
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo Equipo") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Tarjeta de Búsqueda
            Card(modifier = Modifier.padding(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = idTexto,
                        onValueChange = { idTexto = it },
                        label = { Text("Consultar ID") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { idTexto.toIntOrNull()?.let { viewModel.cargarJugadoresPorEquipo(it) } },
                            modifier = Modifier.weight(1f)
                        ) { Text("Cargar") }

                        FilledTonalButton(
                            onClick = { if (idTexto.isNotEmpty()) mostrarConfirmacionBorrar = true },
                            colors = ButtonDefaults.filledTonalButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.weight(1f)
                        ) { Text("Borrar") }
                    }
                }
            }

            // Lista de Jugadores con Cabecera de Equipo
            if (jugadores.isEmpty() && equipoActual == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay datos cargados")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // Cabecera con datos reales del equipo
                    item {
                        equipoActual?.let { info ->
                            EquipoHeaderCard(
                                nombre = info.nombre,
                                ciudad = info.ciudad,
                                fundacion = info.fundacion
                            )
                        }
                    }

                    items(jugadores) { j ->
                        JugadorCard(j.nombre, j.posicion, j.dorsal, j.nacionalidad ?: "N/A", j.goles)
                    }
                }
            }
        }
    }
}

@Composable
fun EquipoHeaderCard(nombre: String, ciudad: String, fundacion: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = "Ciudad: $ciudad | Sede de este Club", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fundación: $fundacion", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun JugadorCard(nombre: String, posicion: String, dorsal: Int?, nacionalidad: String, goles: Int?) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                Text(text = dorsal?.toString() ?: "-", color = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = nombre, fontWeight = FontWeight.Bold)
                Text(text = "$posicion | $nacionalidad", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}