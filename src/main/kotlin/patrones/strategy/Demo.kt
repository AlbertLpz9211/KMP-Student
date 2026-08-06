package patrones.strategy

fun ejecutarDemo() {
    println()
    println("--- Strategy ---")

    val clasica = CompraClasica(EnvioLocal())
    val idiomatica = CompraIdiomatica(envioLocal)

    println("Clasica: ${clasica.calcularTotal(500.0)}")
    println("Idiomatica: ${idiomatica.calcularTotal(500.0)}")
}

fun main() = ejecutarDemo()
