package patrones.memento

fun ejecutarDemo() {
    println()
    println("--- Memento ---")

    val clasico = BorradorPedidoClasico("Ana", "Cuaderno")
    val historial = HistorialPedido()
    historial.agregar(clasico.guardar())
    clasico.producto = "Producto equivocado"
    clasico.restaurar(historial.ultimo())

    val idiomatico = BorradorPedidoIdiomatico("Ana", "Cuaderno")
    val respaldo = idiomatico.snapshot()
    idiomatico.producto = "Producto equivocado"
    idiomatico.restaurar(respaldo)

    println("Clasica: ${clasico.producto}")
    println("Idiomatica: ${idiomatico.producto}")
}

fun main() = ejecutarDemo()