package patrones.memento

class MementoPedido internal constructor(
    internal val cliente: String,
    internal val producto: String
)

class BorradorPedidoClasico(
    var cliente: String = "",
    var producto: String = ""
) {
    // Guarda una copia del estado actual del borrador.
    fun guardar(): MementoPedido = MementoPedido(cliente, producto)

    fun restaurar(memento: MementoPedido) {
        cliente = memento.cliente
        producto = memento.producto
    }
}

class HistorialPedido {
    private val estados = mutableListOf<MementoPedido>()

    fun agregar(memento: MementoPedido) {
        estados.add(memento)
    }

    fun ultimo(): MementoPedido = estados.last()
}
