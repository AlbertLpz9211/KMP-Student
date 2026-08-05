package patrones.bridge

interface DispositivoHuerto {
    fun encender()
    fun apagar()
    fun configurarPotencia(nivel: Int)
    fun obtenerNombre(): String
    fun estaEncendido(): Boolean
    fun obtenerPotenciaActual(): Int
}

class RiegoMecanico : DispositivoHuerto {
    private var encendido = false
    private var potencia = 0

    override fun encender() {
        encendido = true
        println("[Hardware Riego] Válvulas principales abiertas.")
    }

    override fun apagar() {
        encendido = false
        potencia = 0
        println("[Hardware Riego] Válvulas principales cerradas.")
    }

    override fun configurarPotencia(nivel: Int) {
        potencia = nivel
        println("[Hardware Riego] Presión ajustada al $nivel PSI.")
    }

    override fun obtenerNombre(): String = "Sistema de Riego"
    override fun estaEncendido(): Boolean = encendido
    override fun obtenerPotenciaActual(): Int = potencia
}

class IluminacionLEDHardware : DispositivoHuerto {
    private var encendido = false
    private var brillo = 0

    override fun encender() {
        encendido = true
        println("[Hardware LED] Paneles LED encendidos.")
    }

    override fun apagar() {
        encendido = false
        brillo = 0
        println("[Hardware LED] Paneles LED apagados.")
    }

    override fun configurarPotencia(nivel: Int) {
        brillo = nivel
        println("[Hardware LED] Intensidad lumínica fijada al $nivel%.")
    }

    override fun obtenerNombre(): String = "Iluminación LED"
    override fun estaEncendido(): Boolean = encendido
    override fun obtenerPotenciaActual(): Int = brillo
}

abstract class ControlRemotoHuerto(protected val dispositivo: DispositivoHuerto) {
    open fun presionarBotonEncendido() {
        println("Control: Enviando señal de conmutación a ${dispositivo.obtenerNombre()}...")
        if (dispositivo.estaEncendido()) {
            dispositivo.apagar()
        } else {
            dispositivo.encender()
        }
    }

    abstract fun ejecutarComandoEspecial()
}

class ControlDePared(dispositivo: DispositivoHuerto) : ControlRemotoHuerto(dispositivo) {
    override fun ejecutarComandoEspecial() {
        println("Control de Pared: Ajustando dispositivo a potencia estándar (50%).")
        if (!dispositivo.estaEncendido()) dispositivo.encender()
        dispositivo.configurarPotencia(50)
    }
}

class PanelAutomatizado(dispositivo: DispositivoHuerto) : ControlRemotoHuerto(dispositivo) {
    override fun ejecutarComandoEspecial() {
        println("Panel Automatizado: Activando perfil de rendimiento máximo (100%).")
        if (!dispositivo.estaEncendido()) dispositivo.encender()
        dispositivo.configurarPotencia(100)
    }
}