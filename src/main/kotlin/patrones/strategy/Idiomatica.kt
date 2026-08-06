package patrones.strategy

typealias CalculoEnvio = (Double) -> Double

class CompraIdiomatica(
    private var calcularEnvio: CalculoEnvio
) {
    fun cambiarEstrategia(nueva: CalculoEnvio) {
        calcularEnvio = nueva
    }

    fun calcularTotal(subtotal: Double): Double =
        subtotal + calcularEnvio(subtotal)
}

val envioLocal: CalculoEnvio = { 40.0 }
val envioExpress: CalculoEnvio = { 90.0 }