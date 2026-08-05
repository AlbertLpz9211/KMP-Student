package patrones.composite

import kotlin.test.Test
import kotlin.test.assertEquals

class CompositeTest {

    @Test
    fun testPoderDeFuegoSoldadoIndividual() {
        val soldado = SoldadoInfanteria("Test-Soldado", 20)
        assertEquals(20, soldado.calcularPoderDeFuego(), "El poder del soldado individual debe ser exacto")
    }

    @Test
    fun testPoderDeFuegoJerarquiaMilitar() {
        val division = CompaniaMilitar("Division-Test")
        division.incorporar(SoldadoInfanteria("S1", 10))
        division.incorporar(SoldadoInfanteria("S2", 30))

        val subDivision = CompaniaMilitar("SubDivision-Test")
        subDivision.incorporar(SoldadoInfanteria("S3", 10))
        division.incorporar(subDivision)

        assertEquals(50, division.calcularPoderDeFuego(), "El poder total de la división debe sumar todos sus elementos")
    }
}