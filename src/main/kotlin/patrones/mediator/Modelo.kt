package patrones.mediator

interface MediadorSala {
    fun enviar(mensaje: String, origen: Participante)
}

class SalaEstudio : MediadorSala {
    private val participantes = mutableListOf<Participante>()

    fun unir(nombre: String): Participante =
        Participante(nombre, this).also {
            participantes.add(it)
        }

    override fun enviar(mensaje: String, origen: Participante) {
        // El remitente no recibe su propio mensaje.
        participantes
            .filter { it !== origen }
            .forEach {
                it.recibir("${origen.nombre}: $mensaje")
            }
    }
}

class Participante(
    val nombre: String,
    private val sala: MediadorSala
) {
    private val bandeja = mutableListOf<String>()

    val mensajes: List<String>
        get() = bandeja.toList()

    fun enviar(mensaje: String) {
        sala.enviar(mensaje, this)
    }

    internal fun recibir(mensaje: String) {
        bandeja.add(mensaje)
    }
}