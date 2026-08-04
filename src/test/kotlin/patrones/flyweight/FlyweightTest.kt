package patrones.flyweight

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class FlyweightTest {

    @Test
    fun testReutilizacionDeInstancias() {
        // Obtenemos el mismo tipo dos veces
        val tipo1 = FabricaUnidades.getTipo("Arquero", "10", "arco.png")
        val tipo2 = FabricaUnidades.getTipo("Arquero", "10", "arco.png")

        // assertSame verifica que sean EXACTAMENTE el mismo objeto en memoria (misma dirección)
        assertSame(tipo1, tipo2, "La fábrica debería devolver la misma instancia para el mismo tipo")
    }

    @Test
    fun testContadorDeTipos() {
        val inicial = FabricaUnidades.totalTiposCreados()
        
        FabricaUnidades.getTipo("Guerrero", "20", "espada.png")
        FabricaUnidades.getTipo("Mago", "5", "baston.png")
        FabricaUnidades.getTipo("Guerrero", "20", "espada.png") // Repetido

        assertEquals(inicial + 2, FabricaUnidades.totalTiposCreados(), "Solo deberían haberse creado 2 tipos nuevos")
    }
}
