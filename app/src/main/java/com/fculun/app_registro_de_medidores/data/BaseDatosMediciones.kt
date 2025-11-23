package com.fculun.app_registro_de_medidores.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fculun.app_registro_de_medidores.model.Medicion

/**
 * Base de datos Room utilizada por la aplicación.
 *
 * - Define la entidad Medicion como la tabla principal.
 * - Aplica conversores personalizados para almacenar tipos que Room no soporta nativamente
 * - Implementa un patrón singleton para garantizar que solo exista una instancia
 *   de la base de datos durante toda la ejecución de la app.
 */
@Database(
    entities = [Medicion::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Conversores::class)
abstract class BaseDatosMediciones : RoomDatabase() {

    /** DAO utilizado para realizar operaciones CRUD sobre la tabla de mediciones. */
    abstract fun obtenerMedicionDao(): MedicionDao

    companion object {
        @Volatile
        private var instancia: BaseDatosMediciones? = null

        /**
         * Devuelve la instancia única de la base de datos.
         *
         * Si aún no existe, se crea utilizando Room.databaseBuilder.
         * El uso de synchronized garantiza que no se creen múltiples instancias
         * cuando distintos hilos acceden al metodo al mismo tiempo.
         */
        fun obtenerInstancia(contexto: Context): BaseDatosMediciones {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatosMediciones::class.java,
                    "base_datos_mediciones"
                ).build().also { bd ->
                    instancia = bd
                }
            }
        }
    }
}
