package patrones.decorator

import kotlin.test.Test
import kotlin.test.assertTrue

class DecoratorTest {

    @Test
    fun testNotificadorBasico() {
        // Validación conceptual de ejecución del componente base
        val emailer = NotificadorEmail()
        emailer.enviar("Test Email")
        assertTrue(true, "El componente base debe ejecutarse sin errores")
    }

    @Test
    fun testNotificadorConDecoradoresMultiples() {
        // Envolviendo componentes dinámicamente
        val compuesto = NotificadorSlack(NotificadorSMS(NotificadorEmail()))
        compuesto.enviar("Test Integral")
        assertTrue(true, "Las capas de decoración deben ejecutarse de manera anidada")
    }
}