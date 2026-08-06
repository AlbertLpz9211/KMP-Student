```kotlin
package patrones.state

fun ejecutarDemo() {
    println()
    println("--- State ---")

    val clasica = ReservaClasica()
    clasica.avanzar()

    val idiomatica = ReservaIdiomatica()
    idiomatica.avanzar()

    println("Clasica: ${clasica.estado.nombre}")
    println("Idiomatica: ${idiomatica.nombreEstado()}")
}

fun main() = ejecutarDemo()
```