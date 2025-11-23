package com.fculun.app_registro_de_medidores.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fculun.app_registro_de_medidores.model.Medicion
import kotlinx.coroutines.flow.Flow

/**
 * DAO que define las operaciones disponibles
 * para trabajar con la tabla 'mediciones'.
 */
@Dao
interface MedicionDao {

    /**
     * Obtiene todas las mediciones almacenadas ordenadas desde la más reciente.
     *
     * Se retorna un Flow<List<Medicion>> para que la UI pueda observar la
     * base de datos en tiempo real
     */
    @Query("SELECT * FROM mediciones ORDER BY idMedicion DESC")
    fun obtenerTodasLasMediciones(): Flow<List<Medicion>>

    /**
     * Inserta una nueva medición en la base de datos.
     */
    @Insert
    suspend fun insertarMedicion(medicion: Medicion)
}
