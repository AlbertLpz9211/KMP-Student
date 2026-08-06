```kotlin
package patrones.memento

// La data class funciona como una instantanea inmutable.
data class EstadoPedido(
    val cliente: String,
    val producto: String
)

class BorradorPedidoIdiomatico(
    var cliente: String = "",
    var producto: String = ""
) {
    fun snapshot(): EstadoPedido = EstadoPedido(cliente, producto)

    fun restaurar(estado: EstadoPedido) {
        cliente = estado.cliente
        producto = estado.producto
    }
}
```