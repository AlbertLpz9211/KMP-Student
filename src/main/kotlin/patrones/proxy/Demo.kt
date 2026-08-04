package patrones.proxy

/**
 * DEMOSTRACIÓN DEL PATRÓN PROXY
 * El cliente cree que está usando el Internet normal, pero en realidad
 * está pasando por nuestro Firewall (Proxy).
 */
fun main() {
    println("--- INICIANDO SISTEMA DE RED CORPORATIVA ---")

    val internet: InternetService = FirewallProxy()

    val sitiosParaProbar = listOf(
        "google.com",
        "github.com",
        "facebook.com",
        "tiktok.com",
        "YOUTUBE.COM"
    )

    for (sitio in sitiosParaProbar) {
        try {
            internet.conectarA(sitio)
        } catch (e: AccesoDenegadoException) {
            println("[BLOQUEADO] ${e.message}")
        }
    }

    println("\n--- SESIÓN DE RED FINALIZADA ---")
}
