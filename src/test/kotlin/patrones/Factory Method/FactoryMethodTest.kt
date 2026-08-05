package patrones.factorymethod

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// CLASE DE PRUEBAS UNITARIAS
// Aquí validamos que nuestro patrón Factory Method funcione correctamente y cumpla con lo esperado.
class FactoryMethodTest {

    // @Test le indica al framework de pruebas (Kotlin Test / JUnit) que este método es una prueba automatizada.
    @Test
    fun testCreacionProductoDigital() {
        // ARRANGE / ACT:
        // Instanciamos el creador digital usando polimorfismo (tipo general CreadorLogistica).
        val creador: CreadorLogistica = CreadorDigital()

        // Invocamos el Factory Method para obtener el servicio de entrega correspondiente.
        val servicio = creador.crearServicioEntrega()

        // ASSERT:
        // Verificamos que el método del servicio devuelva EXACTAMENTE la cadena esperada ("Descarga Inmediata").
        assertEquals("Descarga Inmediata", servicio.obtenerMetodo())
    }

    // Segunda prueba automatizada para la variante física.
    @Test
    fun testCreacionProductoFisico() {
        // ARRANGE / ACT:
        // Instanciamos el creador físico.
        val creador: CreadorLogistica = CreadorFisico()

        // El Factory Method nos debe regresar la implementación física del servicio.
        val servicio = creador.crearServicioEntrega()

        // ASSERT:
        // Verificamos que el texto devuelto CONTENGA la frase "Envío por Paquetería".
        assertTrue(servicio.obtenerMetodo().contains("Envío por Paquetería"))
    }
}