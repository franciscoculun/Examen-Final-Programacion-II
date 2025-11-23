package com.fculun.app_registro_de_medidores.repository

import com.fculun.app_registro_de_medidores.data.MedicionDao
import com.fculun.app_registro_de_medidores.model.Medicion
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que actúa como capa intermedia entre el ViewModel y Room.
 */
class MedicionesRepository(private val dao: MedicionDao) {

    /**
     * Flujo reactivo con todas las mediciones almacenadas.
     */
    val todasLasMediciones: Flow<List<Medicion>> = dao.obtenerTodasLasMediciones()

    /**
     * Inserta una nueva medición utilizando la función suspendida del DAO.
     */
    suspend fun insertar(medicion: Medicion) {
        dao.insertarMedicion(medicion)
    }
}
