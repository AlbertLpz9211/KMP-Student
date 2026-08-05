package patrones.adapter

/**
 * DEMOSTRACIÓN DEL PATRÓN ADAPTER
 * El cliente (la aplicación principal) espera trabajar con una interfaz moderna y unificada (`ProcesadorPago`),
 * sin embargo, tenemos que integrar un servicio legacy o de terceros (`PasarelaExternaAvanzada`) que tiene
 * un nombre de métodos completamente diferente. El Adapter hace posible que convivan sin modificar al cliente.
 */
fun main() {
    println("--- BIENVENIDO AL SISTEMA DE PAGOS KMP ---")

    // 1. Usando el procesador nativo/estándar de nuestra app
    val pagoEstandar: ProcesadorPago = PagoEstandarMovil()
    pagoEstandar.procesarPago(150.00, "USD")

    println("\n--- INTEGRANDO PASARELA DE TERCEROS MEDIANTE ADAPTER ---")

    // 2. Instanciamos la pasarela externa antigua / de terceros
    val pasarelaExterna = PasarelaExternaAvanzada()

    // 3. La envolvemos en el adaptador para que cumpla con la interfaz que la app entiende
    val adaptadorPago: ProcesadorPago = PasarelaExternaAdapter(pasarelaExterna)

    // 4. El cliente procesa el pago usando exactamente el mismo método estándar,
    //    sin enterarse de que por debajo se está llamando a la API externa adaptada.
    adaptadorPago.procesarPago(450.50, "MXN")

    println("--- DEMOSTRACIÓN FINALIZADA ---")
}