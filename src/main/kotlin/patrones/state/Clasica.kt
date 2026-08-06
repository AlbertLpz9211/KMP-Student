package patrones.state

interface EstadoReservaClasico {
    val nombre: String
    fun avanzar(reserva: ReservaClasica)
}

object PendienteClasico : EstadoReservaClasico {
    override val nombre = "Pendiente"

    override fun avanzar(reserva: ReservaClasica) {
        reserva.estado = ConfirmadaClasico
    }
}

object ConfirmadaClasico : EstadoReservaClasico {
    override val nombre = "Confirmada"

    override fun avanzar(reserva: ReservaClasica) {
        reserva.estado = FinalizadaClasico
    }
}

object FinalizadaClasico : EstadoReservaClasico {
    override val nombre = "Finalizada"

    override fun avanzar(reserva: ReservaClasica) = Unit
}

class ReservaClasica(
    var estado: EstadoReservaClasico = PendienteClasico
) {
    // La reserva delega la transicion al estado actual.
    fun avanzar() = estado.avanzar(this)
}