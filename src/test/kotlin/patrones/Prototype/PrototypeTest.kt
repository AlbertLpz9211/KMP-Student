package patrones.prototype

// Importamos las herramientas necesarias para realizar las pruebas.
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

// Esta clase contiene las pruebas del patrón Prototype.
// Su objetivo es comprobar que la clonación funciona correctamente.
class PrototypeTest {

    // La anotación @Test indica que este método es una prueba automática.
    // Cuando se ejecutan los tests del proyecto, este método se ejecutará.
    @Test
    fun testClonacionDeObjeto() {

        // Se crea el documento original que servirá como prototipo.
        val documentoOriginal = DocumentoBase(
            titulo = "Plantilla",
            paginas = 10
        )

        // Se crea una copia del documento utilizando el método clone().
        val documentoClonado = documentoOriginal.clone()

        // ==========================================================
        // Verificaciones
        // ==========================================================

        // Verifica que el título del documento original
        // y el del documento clonado sean exactamente iguales.
        assertEquals(documentoOriginal.titulo, documentoClonado.titulo)

        // Verifica que el documento original y el clon
        // NO sean el mismo objeto en memoria.
        //
        // Si esta prueba pasa, significa que realmente
        // se creó una copia independiente y no solo otra
        // referencia apuntando al mismo objeto.
        assertNotSame(
            documentoOriginal,
            documentoClonado,
            "El clon debe ser un nuevo objeto independiente"
        )
    }
}