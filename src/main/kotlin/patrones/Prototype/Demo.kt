package patrones.prototype

// Función principal del programa.
// Todo empieza a ejecutarse desde aquí.
fun main() {

    // Solo imprime un título para indicar qué patrón de diseño se va a demostrar.
    println("--- DEMOSTRACIÓN PATRÓN PROTOTYPE ---")

    // ==========================================================
    // PASO 1: Crear el objeto original (prototipo)
    // ==========================================================
    println("\n--- 1. Creación del documento prototipo original ---")

    // Aquí se crea un objeto llamado documentoOriginal.
    // Este objeto será nuestro "prototipo", es decir,
    // el modelo que después podremos copiar.
    //
    // En lugar de crear muchos documentos desde cero,
    // primero hacemos uno y luego generamos copias de él.
    val documentoOriginal = DocumentoBase(
        titulo = "Plantilla Contrato Standard",
        paginas = 15
    )

    // Mostramos los datos del documento original para comprobar
    // que se creó correctamente.
    println("Original -> Título: '${documentoOriginal.titulo}', Páginas: ${documentoOriginal.paginas}")

    // ==========================================================
    // PASO 2: Clonar el objeto original
    // ==========================================================
    println("\n--- 2. Clonación del prototipo ---")

    // Se utiliza el método clone() para crear una copia del documento.
    // El nuevo objeto tendrá la misma información que el original,
    // pero será un objeto completamente independiente.
    val documentoClonado = documentoOriginal.clone()

    // Mostramos los datos del documento clonado.
    // Podemos ver que tiene exactamente los mismos valores.
    println("Clon     -> Título: '${documentoClonado.titulo}', Páginas: ${documentoClonado.paginas}")

    // El operador === compara si ambas variables apuntan exactamente
    // al mismo espacio de memoria.
    //
    // Si el resultado es false significa que son dos objetos diferentes,
    // aunque tengan la misma información.
    // Esto demuestra que realmente se creó una copia del objeto.
    println("¿Son la misma referencia en memoria? ${documentoOriginal === documentoClonado}")

    // Mensaje para indicar que terminó la demostración.
    println("\n--- DEMOSTRACIÓN PROTOTYPE FINALIZADA ---")
}