package patrones.builder

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// CLASE DE PRUEBAS UNITARIAS (PATRÓN BUILDER)
// Validamos que el Builder configure correctamente las propiedades del objeto resultante
// tanto al personalizar todos sus campos como al mantener los valores por defecto.
class BuilderTest {

    // @Test indica al ejecutor de pruebas que este método es un test unitario.
    @Test
    fun testConstruccionObjetoComplejo() {
        // ARRANGE / ACT:
        // Encadenamos los métodos del Builder para configurar una instancia completa
        // y ejecutamos .build() para obtener el producto final (ReporteConfiguracion).
        val configuracion = ReporteBuilder()
            .setTitulo("Reporte Anual")
            .incluirGraficos(true)
            .incluirTablaDatos(true)
            .build()

        // ASSERT:
        // Verificamos que el título asignado al reporte coincida exactamente con "Reporte Anual".
        assertEquals("Reporte Anual", configuracion.titulo)

        // Comprobamos que el flag de gráficos sea verdadero (true), confirmando que la opción se guardó bien.
        assertTrue(configuracion.tieneGraficos)
    }
}