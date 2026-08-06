package patrones.observer

fun ejecutarDemo() {
    println()
    println("--- Observer ---")

    val cursoClasico = CursoClasico("Kotlin basico", 0)
    val alumno = AlumnoObservador("Ana")
    cursoClasico.suscribir(alumno)
    cursoClasico.actualizarCupos(3)

    val cursoIdiomatico = CursoIdiomatico(0)
    var avisoIdiomatico = ""

    cursoIdiomatico.suscribir { cupos ->
        avisoIdiomatico = "Quedan $cupos cupos"
    }

    cursoIdiomatico.cupos = 3

    println("Clasica: ${alumno.ultimoAviso}")
    println("Idiomatica: $avisoIdiomatico")
}

fun main() = ejecutarDemo()