package com.fculun.app_registro_de_medidores.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Entidad que representa una medición almacenada en la base de datos local.
 */
@Entity(tableName = "mediciones")
data class Medicion(

    /** Identificador único del registro. */
    @PrimaryKey(autoGenerate = true)
    val idMedicion: Int = 0,

    /** Tipo de servicio asociado. */
    val tipoServicio: TipoServicio,

    /** Lectura numérica del medidor ingresada por el usuario. */
    val valorMedidor: Int,

    /**
     * Fecha de la medición.
     *
     * Se almacena como LocalDate para mantener coherencia semántica.
     */
    val fechaMedicion: LocalDate
)
