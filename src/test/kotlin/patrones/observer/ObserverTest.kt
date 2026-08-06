package patrones.observer

import kotlin.test.Test
import kotlin.test.assertEquals

class ObserverTest {
    @Test
    fun notificaCuandoCambianLosCupos() {
        val clasico = CursoClasico("Kotlin basico", 0)
        val alumno = AlumnoObservador("Ana")
        clasico.suscribir(alumno)
        clasico.actualizarCupos(2)

        assertEquals(
            "Ana: Kotlin basico tiene 2 cupos",
            alumno.ultimoAviso
        )

        val idiomatico = CursoIdiomatico(0)
        var cuposNotificados = -1

        idiomatico.suscribir {
            cuposNotificados = it
        }

        idiomatico.cupos = 2
        assertEquals(2, cuposNotificados)
    }
}