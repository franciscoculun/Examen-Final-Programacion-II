package com.fculun.app_registro_de_medidores.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fculun.app_registro_de_medidores.R
import com.fculun.app_registro_de_medidores.componentes.ItemMedicion
import com.fculun.app_registro_de_medidores.viewmodel.MedicionViewModel

/**
 * Pantalla principal encargada de mostrar todas las mediciones registradas.
 *
 * Se conecta directamente al ViewModel para observar la lista reactiva de mediciones.
 * Cada cambio proveniente de Room actualiza automáticamente la interfaz.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMedicionesPantalla(
    medicionViewModel: MedicionViewModel,
    onNavegarAFormulario: () -> Unit
) {
    // Observa en tiempo real la lista almacenada en Room mediante StateFlow
    val listaMediciones by medicionViewModel.listaMedicionesEstado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.titulo_mediciones)) }
            )
        },
        floatingActionButton = {
            // Botón flotante para agregar un nuevo registro
            FloatingActionButton(onClick = onNavegarAFormulario) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar medición"
                )
            }
        }

    ) { paddingInterno ->

        // Si no existen mediciones, se muestra mensaje centralizado
        if (listaMediciones.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingInterno)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.sin_mediciones))
            }

            // Si hay mediciones, se despliega la lista dinámica
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingInterno)
                    .fillMaxSize()
            ) {
                items(listaMediciones) { medicionActual ->
                    ItemMedicion(medicion = medicionActual)
                }
            }
        }
    }
}
