package patrones.bridge

interface ReproductorAudio {
    fun cargarArchivo(archivo: String)
    fun iniciarReproduccion()
    fun detenerReproduccion()
    fun establecerVolumen(nivel: Int)
}

class MotorAudioAltavoz : ReproductorAudio {
    private var actual: String = ""
    private var volumenActual: Int = 0

    override fun cargarArchivo(archivo: String) {
        actual = archivo
        println("[Motor Altavoz Físico] Archivo cargado: '$archivo'")
    }

    override fun iniciarReproduccion() {
        println("[Motor Altavoz Físico] Reproduciendo audio en bocinas integradas.")
    }

    override fun detenerReproduccion() {
        println("[Motor Altavoz Físico] Audio pausado o detenido.")
    }

    override fun establecerVolumen(nivel: Int) {
        volumenActual = nivel
        println("[Motor Altavoz Físico] Volumen ajustado a $nivel dB.")
    }
}

class MotorAudioBluetooth : ReproductorAudio {
    private var actual: String = ""
    private var volumenActual: Int = 0

    override fun cargarArchivo(archivo: String) {
        actual = archivo
        println("[Motor Bluetooth] Transmitiendo archivo por aire: '$archivo'")
    }

    override fun iniciarReproduccion() {
        println("[Motor Bluetooth] Reproduciendo stream inalámbrico en audífonos.")
    }

    override fun detenerReproduccion() {
        println("[Motor Bluetooth] Transmisión inalámbrica suspendida.")
    }

    override fun establecerVolumen(nivel: Int) {
        volumenActual = nivel
        println("[Motor Bluetooth] Ganancia Bluetooth fijada en $nivel%.")
    }
}

abstract class InterfazReproductor(protected val motor: ReproductorAudio) {
    open fun reproducirPista(nombre: String) {
        println("Interfaz: Solicitando gestión de pista multimedia...")
        motor.cargarArchivo(nombre)
        motor.iniciarReproduccion()
    }

    abstract fun ajustarAmbienteSonoro(nivel: Int)
}

class InterfazMinimalista(motor: ReproductorAudio) : InterfazReproductor(motor) {
    override fun ajustarAmbienteSonoro(nivel: Int) {
        println("Interfaz Minimalista: Configuración rápida aplicada.")
        motor.establecerVolumen(nivel)
    }
}

class InterfazAvanzadaEcualizada(motor: ReproductorAudio) : InterfazReproductor(motor) {
    override fun ajustarAmbienteSonoro(nivel: Int) {
        println("Interfaz Avanzada: Aplicando perfil con refuerzo de graves y agudos.")
        motor.establecerVolumen(nivel + 5) // Ajuste extra de ecualización
    }
}