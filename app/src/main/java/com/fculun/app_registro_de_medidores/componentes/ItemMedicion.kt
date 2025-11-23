package com.fculun.app_registro_de_medidores.componentes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fculun.app_registro_de_medidores.R
import com.fculun.app_registro_de_medidores.model.Medicion
import com.fculun.app_registro_de_medidores.model.TipoServicio

/**
 * Composable responsable de mostrar una fila de la lista de mediciones.
 *
 * - Un ícono asociado al tipo de servicio (agua, luz, gas).
 * - El tipo de servicio como texto (traducido según el idioma del sistema).
 * - El valor registrado del medidor.
 * - La fecha de la medición.
 *
 * Esta vista se utiliza dentro de la LazyColumn de la pantalla principal.
 */
@SuppressLint("DefaultLocale")
@Composable
fun ItemMedicion(medicion: Medicion) {

    // Selecciona el ícono correspondiente según el tipo de servicio de la medición
    val idIcono = when (medicion.tipoServicio) {
        TipoServicio.AGUA -> R.drawable.agua
        TipoServicio.LUZ  -> R.drawable.luz
        TipoServicio.GAS  -> R.drawable.gas
    }

    // Obtiene la descripción del tipo de servicio desde recursos de texto,
    // permitiendo que se traduzca automáticamente según el idioma del dispositivo
    val textoTipo = when (medicion.tipoServicio) {
        TipoServicio.AGUA -> stringResource(R.string.tipo_agua)
        TipoServicio.LUZ  -> stringResource(R.string.tipo_luz)
        TipoServicio.GAS  -> stringResource(R.string.tipo_gas)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono que identifica visualmente el tipo de servicio
            Image(
                painter = painterResource(id = idIcono),
                contentDescription = textoTipo, // accesible y también traducido
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Fila interna con tres columnas:
            // tipo de servicio, valor del medidor y fecha de la medición
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tipo de servicio (texto)
                Text(
                    text = textoTipo,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                // Valor del medidor, centrado en su columna.
                // Se formatea con separador de miles según configuración regional
                Text(
                    text = "%,d".format(medicion.valorMedidor),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                // Fecha de la medición. Se muestra el LocalDate en formato ISO (yyyy-MM-dd),
                // lo que facilita lectura y coherencia con el formato utilizado en el formulario.
                Text(
                    text = medicion.fechaMedicion.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Línea separadora entre filas para mejorar la legibilidad de la lista
        Divider()
    }
}
