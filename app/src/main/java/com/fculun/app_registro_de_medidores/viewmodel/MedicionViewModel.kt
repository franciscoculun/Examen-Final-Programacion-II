package com.fculun.app_registro_de_medidores.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fculun.app_registro_de_medidores.AplicacionMedidores
import com.fculun.app_registro_de_medidores.model.Medicion
import com.fculun.app_registro_de_medidores.model.TipoServicio
import com.fculun.app_registro_de_medidores.repository.MedicionesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel que coordina tanto la visualización de las mediciones como la lógica
 * del formulario que permite registrar nuevos datos.
 *
 * Su objetivo es aislar la UI del acceso a datos, siguiendo las buenas prácticas.
 */
@RequiresApi(Build.VERSION_CODES.O)
class MedicionViewModel(
    private val repositorio: MedicionesRepository
) : ViewModel() {

    /**
     * Flujo de mediciones expuesto como StateFlow para que Compose pueda observar
     * cambios provenientes de Room sin necesidad de callbacks o LiveData.
     */
    val listaMedicionesEstado = repositorio.todasLasMediciones
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    // --- Estado del formulario ---

    /** Tipo de servicio seleccionado por el usuario. */
    var tipoServicioSeleccionado by mutableStateOf(TipoServicio.AGUA)
        private set

    /** Valor ingresado del medidor. */
    var valorMedidor by mutableStateOf("")
        private set

    /** Fecha ingresada en formato texto. */
    var fechaMedicion by mutableStateOf("")
        private set

    fun actualizarTipoServicioSeleccionado(nuevoTipoServicio: TipoServicio) {
        tipoServicioSeleccionado = nuevoTipoServicio
    }

    fun actualizarValorMedidor(nuevoValorMedidor: String) {
        valorMedidor = nuevoValorMedidor
    }

    fun actualizarFechaMedicion(nuevaFecha: String) {
        fechaMedicion = nuevaFecha
    }

    /**
     * Valida los datos ingresados, crea la entidad `Medicion`
     * y la envía al repositorio para su almacenamiento.
     */
    fun guardarMedicion(Guardar: () -> Unit) {
        val valor = valorMedidor.toIntOrNull() ?: return

        // Validación y conversión de fecha
        val fecha = try {
            LocalDate.parse(fechaMedicion)  // formato ISO: yyyy-MM-dd
        } catch (e: Exception) {
            return
        }

        val nuevaMedicion = Medicion(
            tipoServicio = tipoServicioSeleccionado,
            valorMedidor = valor,
            fechaMedicion = fecha
        )

        viewModelScope.launch {
            repositorio.insertar(nuevaMedicion)
            limpiarFormulario() // Evita que queden datos en pantalla
            Guardar()
        }
    }

    /**
     * Restaura el formulario a su estado inicial.
     */
    fun limpiarFormulario() {
        tipoServicioSeleccionado = TipoServicio.AGUA
        valorMedidor = ""
        fechaMedicion = ""
    }

    // ---------- FACTORY utilizando API moderna ----------

    /**
     * Permite instanciar el ViewModel obteniendo automáticamente
     * el repositorio desde la clase Application.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[
                    ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
                ] as AplicacionMedidores

                MedicionViewModel(app.repositorioMediciones)
            }
        }
    }
}
