```kotlin
package patrones.observer

import kotlin.properties.Delegates

class CursoIdiomatico(cuposIniciales: Int) {
    private val observadores = mutableListOf<(Int) -> Unit>()

    // El delegado ejecuta este bloque cada vez que cambia la propiedad.
    var cupos: Int by Delegates.observable(cuposIniciales) { _, _, nuevoValor ->
        observadores.forEach { it(nuevoValor) }
    }

    fun suscribir(observador: (Int) -> Unit) {
        observadores.add(observador)
    }
}
```