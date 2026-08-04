package patrones.facade

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * TEST DEL PATRÓN FACADE
 * Verifica que al llamar a un comando de la fachada, esta coordine
 * correctamente el estado de todos los subsistemas internos.
 */
class FacadeTest {

    @Test
    fun testActivarModoCrecimientoAcelerado() {
        // 1. Preparamos los subsistemas manualmente para poder vigilarlos
        val riego = SistemaRiego()
        val luces = IluminacionLED()
        val clima = ControlClima()
        val nutrientes = DosificadorNutrientes()

        // 2. Creamos la fachada con esos subsistemas específicos
        val gestor = GestorHuertoFacade(riego, luces, clima, nutrientes)

        // 3. Ejecutamos la acción de la fachada
        gestor.activarModoCrecimientoAcelerado()

        // 4. Verificamos que todos los sistemas cambiaron al estado esperado
        assertTrue(luces.encendida, "Las luces deberían estar encendidas")
        assertEquals("VEGETATIVO", luces.espectro, "El espectro debería ser Vegetativo")
        assertEquals(24, clima.temperaturaC, "La temperatura debería estar a 24 grados")
        assertTrue(clima.ventiladoresActivos, "Los ventiladores deberían estar activos")
        assertEquals("NITRÓGENO ALTO", nutrientes.mezclaPreparada, "La mezcla debería ser de alto nitrógeno")
        assertTrue(riego.valvulasAbiertas, "Las válvulas de riego deberían estar abiertas")
    }

    @Test
    fun testActivarModoAhorro() {
        val riego = SistemaRiego()
        val luces = IluminacionLED()
        val clima = ControlClima()
        
        val gestor = GestorHuertoFacade(riego, luces, clima)

        gestor.activarModoAhorroEnergia()

        assertEquals(30, luces.intensidad, "La intensidad debería bajar al 30%")
        assertEquals(18, clima.temperaturaC, "La temperatura debería bajar a 18 grados")
        assertTrue(!clima.ventiladoresActivos, "Los ventiladores deberían estar apagados")
        assertTrue(!riego.valvulasAbiertas, "El riego debería estar cerrado")
    }
}
