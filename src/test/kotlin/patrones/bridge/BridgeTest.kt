package patrones.bridge

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class BridgeTest {

    @Test
    fun testControlDeParedConRiego() {
        val riego = RiegoMecanico()
        val control = ControlDePared(riego)

        assertFalse(riego.estaEncendido(), "El riego debería iniciar apagado")

        control.ejecutarComandoEspecial()

        assertTrue(riego.estaEncendido(), "El riego debería haberse encendido")
        assertEquals(50, riego.obtenerPotenciaActual(), "La potencia del riego debería ser 50")
    }

    @Test
    fun testPanelAutomatizadoConLuces() {
        val luces = IluminacionLEDHardware()
        val panel = PanelAutomatizado(luces)

        panel.ejecutarComandoEspecial()

        assertTrue(luces.estaEncendido(), "Las luces LED deberían estar encendidas")
        assertEquals(100, luces.obtenerPotenciaActual(), "El brillo de las luces debería ser 100%")

        panel.presionarBotonEncendido()
        assertFalse(luces.estaEncendido(), "Las luces deberían haberse apagado")
    }
}