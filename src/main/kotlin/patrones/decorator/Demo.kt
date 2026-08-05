package patrones.decorator

fun main() {
    println("--- BIENVENIDO AL SISTEMA DE NOTIFICACIONES (DECORATOR) ---")

    println("\n--- 1. Notificador base (Solo Email) ---")
    val notificadorBasico: Notificador = NotificadorEmail()
    notificadorBasico.enviar("Servidor reiniciado con éxito.")

    println("\n--- 2. Notificador decorado con SMS ---")
    val notificadorConSMS: Notificador = NotificadorSMS(NotificadorEmail())
    notificadorConSMS.enviar("Alerta crítica de seguridad detectada.")

    println("\n--- 3. Notificador decorado con SMS y Slack (Múltiples capas) ---")
    val notificadorCompleto: Notificador = NotificadorSlack(NotificadorSMS(NotificadorEmail()))
    notificadorCompleto.enviar("Falla general en el pipeline de despliegue.")

    println("\n--- DEMOSTRACIÓN DECORATOR FINALIZADA ---")
}