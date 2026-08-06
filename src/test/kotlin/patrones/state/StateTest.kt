package patrones.state

import kotlin.test.Test
import kotlin.test.assertEquals

class StateTest {
    @Test
    fun avanzaPorLosEstadosPermitidos() {
        val clasica = ReservaClasica()
        clasica.avanzar()
        assertEquals("Confirmada", clasica.estado.nombre)

        clasica.avanzar()
        assertEquals("Finalizada", clasica.estado.nombre)

        val idiomatica = ReservaIdiomatica()
        idiomatica.avanzar()
        assertEquals("Confirmada", idiomatica.nombreEstado())

        idiomatica.avanzar()
        assertEquals("Finalizada", idiomatica.nombreEstado())
    }
}