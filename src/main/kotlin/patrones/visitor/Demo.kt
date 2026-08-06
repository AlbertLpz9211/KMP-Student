package patrones.visitor

fun ejecutarDemo() {
    println()
    println("--- Visitor ---")

    val visitor = CalculadorImpuestoVisitor()
    val clasico = ElectronicoClasico(1000.0).aceptar(visitor)
    val idiomatico = calcularImpuesto(
        Producto.Electronico(1000.0)
    )

    println("Clasica: $clasico")
    println("Idiomatica: $idiomatico")
}

fun main() = ejecutarDemo()
