package com.fculun.app_registro_de_medidores.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.fculun.app_registro_de_medidores.model.TipoServicio
import java.time.LocalDate

/**
 * Conversores utilizados por Room para almacenar tipos que la base de datos
 * no soporta directamente:
 *
 * - El enum TipoServicio se guarda como String.
 * - LocalDate se convierte usando epochDay para facilitar su almacenamiento.
 *
 * Estos métodos se ejecutan automáticamente cada vez que Room necesita
 * transformar datos hacia/desde la BD.
 */
class Conversores {

    /** Convierte un enum TipoServicio a String para almacenarlo en la BD. */
    @TypeConverter
    fun desdeTipoServicio(tipoServicio: TipoServicio): String {
        return tipoServicio.name
    }

    /** Convierte el String almacenado en la BD nuevamente al enum TipoServicio. */
    @TypeConverter
    fun aTipoServicio(valor: String): TipoServicio {
        return TipoServicio.valueOf(valor)
    }

    /**
     * Convierte un Long a un LocalDate.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromEpochDay(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    /**
     * Convierte un LocalDate a su representación epochDay.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDateToEpochDay(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}
