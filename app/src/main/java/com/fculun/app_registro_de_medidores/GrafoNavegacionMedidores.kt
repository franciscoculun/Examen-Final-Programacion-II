package com.fculun.app_registro_de_medidores

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fculun.app_registro_de_medidores.ui.FormularioMedicionPantalla
import com.fculun.app_registro_de_medidores.viewmodel.MedicionViewModel
import com.fculun.app_registro_de_medidores.ui.ListaMedicionesPantalla

/**
 * Grafo de navegación que define la estructura de rutas de la aplicación.
 *
 * Centraliza todas las pantallas y la forma en que se conectan entre sí.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GrafoNavegacionMedidores(
    navController: NavHostController,
    medicionViewModel: MedicionViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "lista_mediciones" // Ruta inicial
    ) {

        /**
         * Pantalla principal:
         * Muestra la lista de mediciones almacenadas.
         * Desde aquí se navega a la pantalla de formulario.
         */
        composable(route = "lista_mediciones") {
            ListaMedicionesPantalla(
                medicionViewModel = medicionViewModel,
                onNavegarAFormulario = {
                    navController.navigate("formulario_medicion")
                }
            )
        }

        /**
         * Pantalla del formulario:
         * Permite registrar una nueva medición.
         * Al guardar, se vuelve automáticamente a la lista.
         */
        composable(route = "formulario_medicion") {
            FormularioMedicionPantalla(
                medicionViewModel = medicionViewModel,
                onVolverALista = {
                    navController.popBackStack()
                }
            )
        }
    }
}
