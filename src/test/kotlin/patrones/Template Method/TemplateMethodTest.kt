package patrones.templatemethod

// Importamos las herramientas necesarias para realizar pruebas automáticas.
import kotlin.test.Test
import kotlin.test.assertTrue

// Esta clase contiene las pruebas del patrón Template Method.
// Su objetivo es comprobar que el algoritmo definido en la clase padre
// funciona correctamente para diferentes tipos de documentos.
class TemplateMethodTest {

    // La anotación @Test indica que este método es una prueba automática.
    // Se ejecutará cuando se corran los tests del proyecto.
    @Test
    fun testAlgoritmoProcesamientoPDF() {

        // Se crea un procesador de tipo PDF.
        // Aunque el objeto es un ProcesadorPDF, se guarda como
        // ProcesadorDocumento porque ambas clases tienen una relación
        // de herencia.
        //
        // Esto permite utilizar el mismo flujo de procesamiento
        // para diferentes tipos de documentos.
        val procesador: ProcesadorDocumento = ProcesadorPDF()

        // Se ejecuta el método procesar().
        // Este método pertenece a la clase padre y contiene
        // la estructura general del algoritmo.
        //
        // Internamente llamará a los métodos específicos
        // implementados por ProcesadorPDF.
        val resultado = procesador.procesar()

        // Se verifica que el proceso haya terminado correctamente.
        // Si devuelve true significa que el algoritmo completo
        // pudo ejecutarse sin problemas.
        assertTrue(
            resultado,
            "El flujo estructurado en el Template Method debe completarse correctamente"
        )
    }


    // Segunda prueba para comprobar el funcionamiento
    // del patrón Template Method con un archivo Excel.
    @Test
    fun testAlgoritmoProcesamientoExcel() {

        // Se crea un procesador específico para documentos Excel.
        // Al igual que en el caso del PDF, utiliza la estructura
        // general definida por la clase padre.
        val procesador: ProcesadorDocumento = ProcesadorExcel()

        // Se ejecuta el algoritmo completo de procesamiento.
        val resultado = procesador.procesar()

        // Se verifica que el proceso haya finalizado correctamente.
        assertTrue(resultado)
    }
}