package patrones.adapter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * TEST DEL PATRÓN ADAPTER
 * Verifica que el adaptador traduzca correctamente los datos y coordine
 * la llamada al sistema externo con los parámetros adecuados.
 */
class AdapterTest {

    // Clase auxiliar para vigilar los cambios (similar a cómo vigilas los subsistemas en Facade)
    class PasarelaExternaAvanzadaMock : PasarelaExternaAvanzada() {
        var ultimoMonto: Float = 0f
        var ultimaMoneda: String = ""
        var fueLlamada: Boolean = false

        override fun makeInternationalCharge(totalAmount: Float, currencyType: String): Boolean {
            ultimoMonto = totalAmount
            ultimaMoneda = currencyType
            fueLlamada = true
            return true
        }
    }

    @Test
    fun testTraduccionCorrectaDeParametrosEnAdapter() {
        // 1. Preparamos el subsistema externo (mock) manualmente para poder vigilarlo
        val pasarelaExternaMock = PasarelaExternaAvanzadaMock()

        // 2. Creamos el adaptador con ese subsistema específico
        val adaptador = PasarelaExternaAdapter(pasarelaExternaMock)

        // 3. Ejecutamos la acción del adaptador usando la interfaz estándar
        adaptador.procesarPago(1250.75, "EUR")

        // 4. Verificamos que el sistema externo recibió los datos adaptados al formato correcto
        assertTrue(pasarelaExternaMock.fueLlamada, "La pasarela externa debería haber sido llamada")
        assertEquals(1250.75f, pasarelaExternaMock.ultimoMonto, "El monto Double debió convertirse a Float correctamente")
        assertEquals("EUR", pasarelaExternaMock.ultimaMoneda, "La moneda debió ser transmitida intacta")
    }
}