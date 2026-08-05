package patrones.decorator

interface Notificador {
    fun enviar(mensaje: String)
}

class NotificadorEmail : Notificador {
    override fun enviar(mensaje: String) {
        println("[Email] Enviando mensaje: '$mensaje'")
    }
}

abstract class NotificadorDecorator(protected val wrappee: Notificador) : Notificador {
    override fun enviar(mensaje: String) {
        wrappee.enviar(mensaje)
    }
}

class NotificadorSMS(wrappee: Notificador) : NotificadorDecorator(wrappee) {
    override fun enviar(mensaje: String) {
        super.enviar(mensaje)
        enviarSMSAdicional(mensaje)
    }

    private fun enviarSMSAdicional(mensaje: String) {
        println("[SMS] Enviando alerta por texto: '$mensaje'")
    }
}

class NotificadorSlack(wrappee: Notificador) : NotificadorDecorator(wrappee) {
    override fun enviar(mensaje: String) {
        super.enviar(mensaje)
        enviarSlackAdicional(mensaje)
    }

    private fun enviarSlackAdicional(mensaje: String) {
        println("[Slack] Publicando en canal de equipo: '$mensaje'")
    }
}