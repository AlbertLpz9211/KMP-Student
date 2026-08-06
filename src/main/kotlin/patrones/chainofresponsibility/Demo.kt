package patrones.chainofresponsibility

fun ejecutarDemo() {
    println()
    println("--- Chain of Responsibility ---")

    val solicitud = SolicitudPrestamo(
        alumno = "Eduardo",
        horas = 2,
        equipoDisponible = false
    )

    val resultadoClasico = crearCadenaClasica().validar(solicitud)
    val resultadoIdiomatico = validarSolicitud(solicitud)

    println("Clasica: $resultadoClasico")
    println("Idiomatica: $resultadoIdiomatico")
}

fun main() = ejecutarDemo()