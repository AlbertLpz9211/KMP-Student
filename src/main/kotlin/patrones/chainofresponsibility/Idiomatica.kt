package patrones.chainofresponsibility

typealias ReglaSolicitud = (SolicitudPrestamo) -> String?

// Cada lambda representa una regla de la cadena.
val reglasPrestamo: List<ReglaSolicitud> = listOf(
    { solicitud ->
        if (solicitud.alumno.isBlank()) {
            "Falta el nombre del alumno"
        } else {
            null
        }
    },
    { solicitud ->
        if (solicitud.horas !in 1..4) {
            "El prestamo debe durar de 1 a 4 horas"
        } else {
            null
        }
    },
    { solicitud ->
        if (!solicitud.equipoDisponible) {
            "No hay equipo disponible"
        } else {
            null
        }
    }
)

fun validarSolicitud(
    solicitud: SolicitudPrestamo,
    reglas: List<ReglaSolicitud> = reglasPrestamo
): String {
    for (regla in reglas) {
        val error = regla(solicitud)

        // El primer error encontrado detiene el recorrido.
        if (error != null) {
            return error
        }
    }

    return "Solicitud aprobada"
}

