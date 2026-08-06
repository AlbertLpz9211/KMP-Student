package patrones.chainofresponsibility

data class SolicitudPrestamo(
    val alumno: String,
    val horas: Int,
    val equipoDisponible: Boolean
)

abstract class ValidadorSolicitud(
    private val siguiente: ValidadorSolicitud? = null
) {
    fun validar(solicitud: SolicitudPrestamo): String {
        val error = revisar(solicitud)

        // Si existe un error, la cadena se detiene.
        return error ?: siguiente?.validar(solicitud) ?: "Solicitud aprobada"
    }

    protected abstract fun revisar(solicitud: SolicitudPrestamo): String?
}

class ValidarAlumno(siguiente: ValidadorSolicitud? = null) :
    ValidadorSolicitud(siguiente) {

    override fun revisar(solicitud: SolicitudPrestamo): String? =
        if (solicitud.alumno.isBlank()) {
            "Falta el nombre del alumno"
        } else {
            null
        }
}

class ValidarHoras(siguiente: ValidadorSolicitud? = null) :
    ValidadorSolicitud(siguiente) {

    override fun revisar(solicitud: SolicitudPrestamo): String? =
        if (solicitud.horas !in 1..4) {
            "El prestamo debe durar de 1 a 4 horas"
        } else {
            null
        }
}

class ValidarDisponibilidad : ValidadorSolicitud() {

    override fun revisar(solicitud: SolicitudPrestamo): String? =
        if (!solicitud.equipoDisponible) {
            "No hay equipo disponible"
        } else {
            null
        }
}

// Define el orden en que se ejecutan las validaciones.
fun crearCadenaClasica(): ValidadorSolicitud =
    ValidarAlumno(
        ValidarHoras(
            ValidarDisponibilidad()
        )
    )
