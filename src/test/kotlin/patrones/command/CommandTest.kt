package patrones.command

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CommandTest {

    @Test
    fun ejecutaYDeshaceLaMismaAccion() {
        val listaClasica = ListaTareas()
        val clasico = CompletarTareaCommand(
            listaClasica,
            "Entregar tarea"
        )

        clasico.ejecutar()
        assertTrue(listaClasica.estaCompletada("Entregar tarea"))

        clasico.deshacer()
        assertFalse(listaClasica.estaCompletada("Entregar tarea"))

        val listaIdiomatica = ListaTareas()
        val idiomatico = comandoCompletar(
            listaIdiomatica,
            "Entregar tarea"
        )

        idiomatico.ejecutar()
        assertTrue(listaIdiomatica.estaCompletada("Entregar tarea"))

        idiomatico.deshacer()
        assertFalse(listaIdiomatica.estaCompletada("Entregar tarea"))
    }
}