package patrones.singleton

// Importamos las herramientas necesarias para realizar pruebas automáticas.
import kotlin.test.Test
import kotlin.test.assertSame

// Esta clase contiene las pruebas del patrón Singleton.
// Su objetivo es comprobar que siempre exista una sola instancia de la clase.
class SingletonTest {

    // La anotación @Test indica que este método es una prueba automática.
    // Se ejecutará cuando corramos los tests del proyecto.
    @Test
    fun testInstanciaUnicaGlobal() {

        // Se obtiene la primera referencia a la instancia del Singleton.
        val conexionA = ConexionBaseDatos.instancia

        // Se vuelve a solicitar la instancia.
        // Como es un Singleton, no se crea un objeto nuevo,
        // sino que se devuelve exactamente el mismo.
        val conexionB = ConexionBaseDatos.instancia

        // assertSame verifica que ambas variables apunten
        // al mismo objeto en memoria.
        //
        // Si la prueba pasa, significa que realmente existe
        // una única instancia compartida por todo el programa.
        assertSame(
            conexionA,
            conexionB,
            "Ambas referencias deben apuntar a la misma instancia en memoria"
        )
    }
}