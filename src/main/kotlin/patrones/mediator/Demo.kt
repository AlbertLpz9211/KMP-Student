package patrones.mediator

fun ejecutarDemo() {
    println()
    println("--- Mediator ---")

    val sala = SalaEstudio()
    val ana = sala.unir("Ana")
    val luis = sala.unir("Luis")

    ana.enviar("Ya terminaron el ejercicio?")

    println(luis.mensajes.first())
}

fun main() = ejecutarDemo()