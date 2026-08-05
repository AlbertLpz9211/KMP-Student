package patrones.prototype

// Esta interfaz representa el prototipo.
// Su objetivo es obligar a cualquier clase que la implemente
// a tener un método llamado clone(), el cual servirá para crear copias.
interface PrototipoDocumento {

    // Método que se encargará de clonar un objeto.
    // Cada clase decidirá cómo realizar esa copia.
    fun clone(): PrototipoDocumento
}

// Esta clase representa un documento que podrá ser clonado.
// Implementa la interfaz PrototipoDocumento, por lo que está
// obligada a definir el método clone().
class DocumentoBase(

    // Título del documento.
    val titulo: String,

    // Cantidad de páginas que tiene el documento.
    val paginas: Int

) : PrototipoDocumento {

    // Sobrescribimos el método clone() definido en la interfaz.
    // Aquí es donde realmente se crea una copia del objeto.
    override fun clone(): DocumentoBase {

        // Se crea un nuevo objeto DocumentoBase utilizando
        // exactamente los mismos valores del objeto actual.
        //
        // "this" hace referencia al objeto que está llamando
        // al método clone().
        return DocumentoBase(
            titulo = this.titulo,
            paginas = this.paginas
        )
    }
}