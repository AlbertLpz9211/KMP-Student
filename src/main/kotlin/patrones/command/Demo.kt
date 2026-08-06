package patrones.command

fun ejecutarDemo() {
    println()
    println("--- Command ---")

    val listaClasica = ListaTareas()
    val clasico = CompletarTareaCommand(
        listaClasica,
        "Revisar presentacion"
    )

    clasico.ejecutar()
    clasico.deshacer()

    val listaIdiomatica = ListaTareas()
    val idiomatico = comandoCompletar(
        listaIdiomatica,
        "Revisar presentacion"
    )

    idiomatico.ejecutar()

    println(
        "Clasica despues de deshacer: " +
                listaClasica.estaCompletada("Revisar presentacion")
    )

    println(
        "Idiomatica ejecutada: " +
                listaIdiomatica.estaCompletada("Revisar presentacion")
    )
}

fun main() = ejecutarDemo()