package patrones.visitor

import kotlin.test.Test
import kotlin.test.assertEquals

class VisitorTest {
    @Test
    fun aplicaUnaOperacionDistintaSegunElProducto() {
        val visitor = CalculadorImpuestoVisitor()

        assertEquals(
            0.0,
            LibroClasico(300.0).aceptar(visitor)
        )

        assertEquals(
            160.0,
            ElectronicoClasico(1000.0).aceptar(visitor)
        )

        assertEquals(
            160.0,
            calcularImpuesto(Producto.Electronico(1000.0))
        )
    }
}
