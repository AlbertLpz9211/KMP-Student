package patrones.chainofresponsibility

import kotlin.test.Test
import kotlin.test.assertEquals

class ChainOfResponsibilityTest {

    @Test
    fun rechazaUnaSolicitudCuandoNoHayEquipo() {
        val solicitud = SolicitudPrestamo(
            alumno = "Ana",
            horas = 2,
            equipoDisponible = false
        )

        assertEquals(
            "No hay equipo disponible",
            crearCadenaClasica().validar(solicitud)
        )

        assertEquals(
            "No hay equipo disponible",
            validarSolicitud(solicitud)
        )
    }
}