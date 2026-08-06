```kotlin
package patrones.memento

import kotlin.test.Test
import kotlin.test.assertEquals

class MementoTest {
    @Test
    fun restauraElBorradorAnterior() {
        val clasico = BorradorPedidoClasico("Luis", "Mochila")
        val historial = HistorialPedido()
        historial.agregar(clasico.guardar())
        clasico.producto = "Calculadora"
        clasico.restaurar(historial.ultimo())
        assertEquals("Mochila", clasico.producto)

        val idiomatico = BorradorPedidoIdiomatico("Luis", "Mochila")
        val respaldo = idiomatico.snapshot()
        idiomatico.producto = "Calculadora"
        idiomatico.restaurar(respaldo)
        assertEquals("Mochila", idiomatico.producto)
    }
}
```