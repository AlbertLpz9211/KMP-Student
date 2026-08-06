package patrones.mediator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MediatorTest {

    @Test
    fun entregaElMensajeAQuienesNoSonElRemitente() {
        val sala = SalaEstudio()
        val ana = sala.unir("Ana")
        val luis = sala.unir("Luis")

        ana.enviar("Hola")

        assertEquals(
            listOf("Ana: Hola"),
            luis.mensajes
        )

        assertTrue(ana.mensajes.isEmpty())
    }
}