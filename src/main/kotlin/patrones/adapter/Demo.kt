package patrones.adapter

/**
 * --- INTERFAZ TARGET (Dominio de nuestra App) ---
 * Es la interfaz moderna que nuestra aplicación KMP espera utilizar.
 */
interface ProcesadorPago {
    fun procesarPago(monto: Double, moneda: String)
}

/**
 * --- IMPLEMENTACIÓN CONCRETA (Nativa/Estándar) ---
 */
class PagoEstandarMovil : ProcesadorPago {
    override fun procesarPago(monto: Double, moneda: String) {
        println("[App Móvil] Procesando pago estándar de $monto $moneda a través de pasarela interna.")
    }
}

/**
 * --- ADAPTEE (Subsistema Externo o Legacy incompatible) ---
 * Le agregamos 'open' a la clase y al método para poder crear un Mock en los tests.
 */
open class PasarelaExternaAvanzada {
    open fun makeInternationalCharge(totalAmount: Float, currencyType: String): Boolean {
        println("[SDK Externo] Conectando con servidor externo...")
        println("[SDK Externo] Cargo internacional exitoso de $currencyType $totalAmount.")
        return true
    }
}

/**
 * --- ADAPTER (Adaptador) ---
 * Implementa la interfaz target (`ProcesadorPago`) y contiene una instancia del Adaptee (`PasarelaExternaAvanzada`).
 */
class PasarelaExternaAdapter(
    private val pasarelaExterna: PasarelaExternaAvanzada
) : ProcesadorPago {

    override fun procesarPago(monto: Double, moneda: String) {
        println("[Adapter] Traduciendo 'procesarPago' a 'makeInternationalCharge' de la pasarela externa.")

        val montoFloat = monto.toFloat()
        val exito = pasarelaExterna.makeInternationalCharge(montoFloat, moneda)

        if (!exito) {
            throw IllegalStateException("El pago externo ha fallado.")
        }
    }
}