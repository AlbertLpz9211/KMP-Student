package patrones.strategy

import kotlin.test.Test
import kotlin.test.assertEquals

class StrategyTest {
    @Test
    fun cambiaElCalculoSinModificarLaCompra() {
        val clasica = CompraClasica(EnvioLocal())
        assertEquals(540.0, clasica.calcularTotal(500.0))

        clasica.cambiarEstrategia(EnvioExpress())
        assertEquals(590.0, clasica.calcularTotal(500.0))

        val idiomatica = CompraIdiomatica(envioLocal)
        assertEquals(540.0, idiomatica.calcularTotal(500.0))

        idiomatica.cambiarEstrategia(envioExpress)
        assertEquals(590.0, idiomatica.calcularTotal(500.0))
    }
}
