package patrones.observer

interface ObservadorCupo {
    fun actualizar(curso: String, cupos: Int)
}

class CursoClasico(
    private val nombre: String,
    cuposIniciales: Int
) {
    private val observadores = mutableListOf<ObservadorCupo>()

    var cupos: Int = cuposIniciales
        private set

    fun suscribir(observador: ObservadorCupo) {
        observadores.add(observador)
    }

    fun actualizarCupos(nuevosCupos: Int) {
        cupos = nuevosCupos

        // Notifica a los observadores despues de cambiar el cupo.
        observadores.forEach { it.actualizar(nombre, cupos) }
    }
}

class AlumnoObservador(private val nombre: String) : ObservadorCupo {
    var ultimoAviso: String = ""
        private set

    override fun actualizar(curso: String, cupos: Int) {
        ultimoAviso = "$nombre: $curso tiene $cupos cupos"
    }
}
