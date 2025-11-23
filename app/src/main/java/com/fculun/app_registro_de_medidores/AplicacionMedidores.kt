package com.fculun.app_registro_de_medidores

import android.app.Application
import com.fculun.app_registro_de_medidores.data.BaseDatosMediciones
import com.fculun.app_registro_de_medidores.repository.MedicionesRepository

/**
 * Se inicializan dependencias globales como:
 * - la base de datos Room,
 * - y el repositorio de mediciones.
 *
 * Esto permite que el ViewModel utilice el repositorio sin necesidad
 * de que la Activity construya manualmente estas instancias.
 */
class AplicacionMedidores : Application() {

    /**
     * Base de datos Room (creada solo una vez).
     * El uso de `lazy` garantiza inicialización diferida.
     */
    val baseDatos by lazy {
        BaseDatosMediciones.obtenerInstancia(this)
    }

    /**
     * Repositorio que encapsula el acceso al DAO.
     * Es compartido por toda la aplicación, lo que facilita
     * una arquitectura limpia y reutilizable.
     */
    val repositorioMediciones by lazy {
        MedicionesRepository(baseDatos.obtenerMedicionDao())
    }
}
