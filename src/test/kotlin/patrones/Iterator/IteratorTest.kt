package patrones.iterator

// Importamos las herramientas necesarias para crear pruebas automáticas.
import kotlin.test.Test
import kotlin.test.assertEquals

// Esta clase contiene las pruebas del patrón Iterator.
// Su objetivo es comprobar que el recorrido de la colección funciona correctamente.
class IteratorTest {

    // La anotación @Test indica que este método es una prueba automática.
    // Se ejecutará cuando corramos los tests del proyecto.
    @Test
    fun testRecorridoColeccion() {

        // Se crea una colección con tres nombres.
        val coleccion = ColeccionNombres(
            listOf("Alice", "Bob", "Charlie")
        )

        // Se obtiene un iterador para recorrer la colección.
        val iterador = coleccion.crearIterador()

        // Se crea una lista vacía donde se irán guardando
        // los nombres obtenidos durante el recorrido.
        val resultado = mutableListOf<String>()

        // Mientras existan elementos en la colección,
        // se obtiene cada uno y se agrega a la lista resultado.
        while (iterador.tieneSiguiente()) {
            resultado.add(iterador.siguiente())
        }

        // ==========================================================
        // Verificaciones
        // ==========================================================

        // Verifica que se hayan recorrido exactamente tres elementos.
        assertEquals(3, resultado.size)

        // Verifica que el primer elemento recorrido
        // sea "Alice", comprobando que el orden se mantuvo correctamente.
        assertEquals("Alice", resultado.first())
    }
}