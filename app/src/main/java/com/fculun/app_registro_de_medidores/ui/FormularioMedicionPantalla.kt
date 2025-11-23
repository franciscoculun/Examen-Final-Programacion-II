package com.fculun.app_registro_de_medidores.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.fculun.app_registro_de_medidores.R
import com.fculun.app_registro_de_medidores.model.TipoServicio
import com.fculun.app_registro_de_medidores.viewmodel.MedicionViewModel

/**
 * Pantalla que permite al usuario ingresar una nueva medición.
 *
 * Usa estado local para los campos del formulario y delega la
 * persistencia de datos al ViewModel.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMedicionPantalla(
    medicionViewModel: MedicionViewModel,
    onVolverALista: () -> Unit
) {
    // Copia local del estado del formulario, inicializada desde el ViewModel
    var tipoServicioLocal by rememberSaveable { mutableStateOf(medicionViewModel.tipoServicioSeleccionado) }
    var valorMedidorLocal by rememberSaveable { mutableStateOf(medicionViewModel.valorMedidor) }
    var fechaMedicionLocal by rememberSaveable { mutableStateOf(medicionViewModel.fechaMedicion) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.titulo_nueva_medicion)) }
            )
        }
    ) { paddingInterno ->

        Column(
            modifier = Modifier
                .padding(paddingInterno)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {

            // Tarjeta con los campos de valor del medidor y fecha
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(stringResource(R.string.etiqueta_medidor))

                    OutlinedTextField(
                        value = valorMedidorLocal,
                        onValueChange = { valorMedidorLocal = it },
                        singleLine = true,
                        placeholder = { Text("10200") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(stringResource(R.string.etiqueta_fecha))

                    OutlinedTextField(
                        value = fechaMedicionLocal,
                        onValueChange = { fechaMedicionLocal = it },
                        singleLine = true,
                        placeholder = { Text("2025-11-18") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selección del tipo de medidor mediante radio buttons
            Text(stringResource(R.string.etiqueta_tipo_medidor))

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = tipoServicioLocal == TipoServicio.AGUA,
                        onClick = { tipoServicioLocal = TipoServicio.AGUA },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = tipoServicioLocal == TipoServicio.AGUA,
                        onClick = { tipoServicioLocal = TipoServicio.AGUA }
                    )
                    Text(stringResource(R.string.tipo_agua))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = tipoServicioLocal == TipoServicio.LUZ,
                        onClick = { tipoServicioLocal = TipoServicio.LUZ },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = tipoServicioLocal == TipoServicio.LUZ,
                        onClick = { tipoServicioLocal = TipoServicio.LUZ }
                    )
                    Text(stringResource(R.string.tipo_luz))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = tipoServicioLocal == TipoServicio.GAS,
                        onClick = { tipoServicioLocal = TipoServicio.GAS },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = tipoServicioLocal == TipoServicio.GAS,
                        onClick = { tipoServicioLocal = TipoServicio.GAS }
                    )
                    Text(stringResource(R.string.tipo_gas))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón que envía los datos al ViewModel y dispara el guardado en la BD
            Button(
                onClick = {
                    medicionViewModel.actualizarTipoServicioSeleccionado(tipoServicioLocal)
                    medicionViewModel.actualizarValorMedidor(valorMedidorLocal)
                    medicionViewModel.actualizarFechaMedicion(fechaMedicionLocal)

                    medicionViewModel.guardarMedicion {
                        onVolverALista()
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(220.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(stringResource(R.string.boton_guardar))
            }
        }
    }
}
