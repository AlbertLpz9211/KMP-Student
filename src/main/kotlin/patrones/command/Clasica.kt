package patrones.command

class ListaTareas {
    // Guarda los nombres de las tareas completadas.
    private val completadas = mutableSetOf<String>()

    fun completar(tarea: String) {
        completadas.add(tarea)
    }

    fun reabrir(tarea: String) {
        completadas.remove(tarea)
    }

    fun estaCompletada(tarea: String): Boolean = tarea in completadas
}

interface ComandoTarea {
    fun ejecutar()
    fun deshacer()
}

class CompletarTareaCommand(
    private val lista: ListaTareas,
    private val tarea: String
) : ComandoTarea {

    override fun ejecutar() = lista.completar(tarea)

    override fun deshacer() = lista.reabrir(tarea)
}