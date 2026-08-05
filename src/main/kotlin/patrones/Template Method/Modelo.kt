package patrones.templatemethod

// Esta clase abstracta representa la clase principal del patrón Template Method.
// Contiene la estructura general del proceso que deben seguir todos los documentos.
abstract class ProcesadorDocumento {

    // Este es el método plantilla (Template Method).
    // Define el orden de los pasos que se deben realizar al procesar un documento.
    //
    // Las clases hijas no modifican este orden, solamente implementan
    // las partes específicas que necesitan cambiar.
    fun procesar(): Boolean {

        // Primer paso: abrir el archivo.
        // La implementación dependerá del tipo de documento.
        abrirArchivo()

        // Segundo paso: extraer el contenido del archivo.
        // Cada tipo de documento tendrá su propia forma de hacerlo.
        extraerContenido()

        // Tercer paso: cerrar el archivo.
        // Este paso es igual para todos los documentos,
        // por eso se encuentra directamente en la clase padre.
        cerrarArchivo()

        // Retorna true indicando que el proceso terminó correctamente.
        return true
    }

    // Estos métodos son abstractos porque la clase padre sabe
    // qué pasos existen, pero no sabe exactamente cómo realizarlos.
    //
    // Las clases hijas están obligadas a crear su propia implementación.
    protected abstract fun abrirArchivo()

    protected abstract fun extraerContenido()


    // Este método es privado porque todos los documentos se cierran
    // de la misma manera y no necesita ser modificado por las clases hijas.
    private fun cerrarArchivo() {
        println("Archivo cerrado correctamente")
    }
}


// Esta clase representa un procesador específico para archivos PDF.
// Hereda de ProcesadorDocumento y completa los pasos que son diferentes.
class ProcesadorPDF : ProcesadorDocumento() {

    // Implementación del método para abrir archivos PDF.
    override fun abrirArchivo() {
        println("Abriendo archivo PDF...")
    }

    // Implementación del método para extraer información de un PDF.
    override fun extraerContenido() {
        println("Extrayendo texto y vectores del PDF...")
    }
}


// Esta clase representa un procesador específico para archivos Excel.
// También hereda la estructura general de ProcesadorDocumento.
class ProcesadorExcel : ProcesadorDocumento() {

    // Implementación del método para abrir archivos Excel.
    override fun abrirArchivo() {
        println("Abriendo hoja de cálculo Excel...")
    }

    // Implementación del método para extraer información de un Excel.
    override fun extraerContenido() {
        println("Extrayendo celdas y fórmulas del Excel...")
    }
}