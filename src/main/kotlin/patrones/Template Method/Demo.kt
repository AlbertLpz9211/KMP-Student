package patrones.templatemethod

// Función principal del programa.
// Desde aquí inicia la demostración del patrón Template Method.
fun main() {

    // Imprime un título para indicar qué patrón de diseño se va a demostrar.
    println("--- DEMOSTRACIÓN PATRÓN TEMPLATE METHOD ---")

    // ==========================================================
    // PASO 1: Procesamiento de un documento PDF
    // ==========================================================
    println("\n--- 1. Procesando documento PDF ---")

    // Se crea un objeto de tipo ProcesadorPDF.
    // Se guarda en una variable del tipo ProcesadorDocumento,
    // porque ambas clases tienen una relación de herencia.
    //
    // Esto permite trabajar con diferentes tipos de procesadores
    // utilizando la misma estructura general.
    val procesadorPDF: ProcesadorDocumento = ProcesadorPDF()

    // Se llama al método procesar().
    // Este método pertenece a la clase padre y contiene
    // los pasos generales del algoritmo.
    //
    // Los pasos específicos del PDF serán realizados
    // por la clase hija.
    procesadorPDF.procesar()


    // ==========================================================
    // PASO 2: Procesamiento de un documento Excel
    // ==========================================================
    println("\n--- 2. Procesando documento Excel ---")

    // Se crea un procesador específico para archivos Excel.
    // Aunque es un objeto diferente al PDF, utiliza la misma
    // estructura definida por la clase base.
    val procesadorExcel: ProcesadorDocumento = ProcesadorExcel()

    // Se ejecuta nuevamente el método procesar().
    // El flujo general se mantiene igual, pero los pasos
    // específicos cambian según el tipo de documento.
    procesadorExcel.procesar()


    // Mensaje para indicar que terminó la demostración.
    println("\n--- DEMOSTRACIÓN TEMPLATE METHOD FINALIZADA ---")
}