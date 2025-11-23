package com.fculun.app_registro_de_medidores

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.fculun.app_registro_de_medidores.viewmodel.MedicionViewModel

/**
 * Activity principal.
 *
 * Se encarga de:
 * - inicializar la navegación,
 * - obtener el ViewModel usando la factory,
 * - y entregar ambos a la capa de UI.
 */
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Controlador de navegación para Compose
            val controladorNavegacion = rememberNavController()

            // ViewModel instanciado mediante la factory definida en el companion object.
            // Esto permite obtener el repositorio desde la clase Application.
            val medicionViewModel: MedicionViewModel =
                viewModel(factory = MedicionViewModel.Factory)

            // Grafo de navegación que define todas las pantallas de la app
            GrafoNavegacionMedidores(
                navController = controladorNavegacion,
                medicionViewModel = medicionViewModel
            )
        }
    }
}
