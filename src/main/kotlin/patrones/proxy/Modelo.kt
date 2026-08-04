package patrones.proxy

/**
 * INTERFAZ COMÚN: Define las operaciones para el objeto real y el proxy.
 * Ambos deben parecer iguales ante el cliente.
 */
interface InternetService {
    fun conectarA(url: String)
}

/**
 * OBJETO REAL: El servicio que realmente tiene acceso a la red.
 * Es una operación "costosa" o "sensible" que queremos proteger.
 */
class InternetReal : InternetService {
    override fun conectarA(url: String) {
        println("[SERVIDOR] Estableciendo conexión segura con: $url... ¡Conectado!")
    }
}

class AccesoDenegadoException(mensaje: String) : Exception(mensaje)

/**
 * PROXY (Protection Proxy): Actúa como un Firewall.
 * Controla el acceso al objeto real basándose en una lista negra.
 */
class FirewallProxy : InternetService {
    // Usamos 'by lazy' para demostrar inicialización perezosa (Kotlin Idiomático)
    // El objeto real solo se crea si el acceso es permitido al menos una vez.
    private val internetReal by lazy { InternetReal() }

    private val sitiosBloqueados = listOf(
        "facebook.com",
        "instagram.com",
        "tiktok.com",
        "youtube.com"
    )

    override fun conectarA(url: String) {
        val urlLimpia = url.lowercase().trim()
        
        // El Proxy decide si permite la conexión o no
        if (sitiosBloqueados.any { urlLimpia.contains(it) }) {
            throw AccesoDenegadoException("FIREWALL: Acceso denegado a '$url'")
        } else {
            // Si es seguro, el Proxy delega la tarea al objeto real
            internetReal.conectarA(url)
        }
    }
}
