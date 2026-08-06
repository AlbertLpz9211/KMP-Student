```kotlin
package patrones.state

sealed interface EstadoReserva {
    object Pendiente : EstadoReserva
    object Confirmada : EstadoReserva
    object Finalizada : EstadoReserva
}

class ReservaIdiomatica(
    var estado: EstadoReserva = EstadoReserva.Pendiente
) {
    fun avanzar() {
        // El when contempla todos los estados posibles.
        estado = when (estado) {
            EstadoReserva.Pendiente -> EstadoReserva.Confirmada
            EstadoReserva.Confirmada -> EstadoReserva.Finalizada
            EstadoReserva.Finalizada -> EstadoReserva.Finalizada
        }
    }

    fun nombreEstado(): String = when (estado) {
        EstadoReserva.Pendiente -> "Pendiente"
        EstadoReserva.Confirmada -> "Confirmada"
        EstadoReserva.Finalizada -> "Finalizada"
    }
}
```