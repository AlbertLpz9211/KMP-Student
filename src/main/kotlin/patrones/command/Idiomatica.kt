package patrones.command

class ComandoLambda(
    val ejecutar: () -> Unit,
    val deshacer: () -> Unit
)

fun comandoCompletar(lista: ListaTareas, tarea: String): ComandoLambda =
    ComandoLambda(
        ejecutar = { lista.completar(tarea) },
        deshacer = { lista.reabrir(tarea) }
    )