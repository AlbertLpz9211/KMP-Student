package patrones.strategy

interface EstrategiaEnvio {
    fun calcular(subtotal: Double): Double
}

class EnvioLocal : EstrategiaEnvio {
    override fun calcular(subtotal: Double): Double = 40.0
}

class EnvioExpress : EstrategiaEnvio {
    override fun calcular(subtotal: Double): Double = 90.0
}

class CompraClasica(
    private var estrategia: EstrategiaEnvio
) {
    fun cambiarEstrategia(nueva: EstrategiaEnvio) {
        estrategia = nueva
    }

    // La compra delega el calculo a la estrategia seleccionada.
    fun calcularTotal(subtotal: Double): Double =
        subtotal + estrategia.calcular(subtotal)
}
