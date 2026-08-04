package patrones.facade

/**
 * --- SUBSISTEMA COMPLEJO ---
 * Estos son los componentes técnicos del invernadero. Funcionan de forma independiente
 * y tienen una lógica detallada que el usuario no debería tener que manejar siempre.
 */

class SistemaRiego {
    var valvulasAbiertas = false
        private set
    var presionPsi = 0
        private set

    fun abrirValvulas() {
        valvulasAbiertas = true
        println("[Riego] Válvulas abiertas.")
    }

    fun cerrarValvulas() {
        valvulasAbiertas = false
        println("[Riego] Válvulas cerradas.")
    }

    fun establecerPresion(psi: Int) {
        presionPsi = psi
        println("[Riego] Presión configurada a $psi PSI.")
    }
}

class IluminacionLED {
    var encendida = false
        private set
    var espectro = "NINGUNO"
        private set
    var intensidad = 0
        private set

    fun encender() {
        encendida = true
        println("[Luces] Luces LED encendidas.")
    }

    fun apagar() {
        encendida = false
        println("[Luces] Luces LED apagadas.")
    }

    fun ajustarEspectro(tipo: String) {
        espectro = tipo
        println("[Luces] Espectro ajustado para: $tipo.")
    }

    fun setIntensidad(nivel: Int) {
        intensidad = nivel
        println("[Luces] Intensidad al $nivel%.")
    }
}

class ControlClima {
    var ventiladoresActivos = false
        private set
    var temperaturaC = 0
        private set

    fun activarVentiladores() {
        ventiladoresActivos = true
        println("[Clima] Ventiladores en marcha.")
    }

    fun desactivarVentiladores() {
        ventiladoresActivos = false
        println("[Clima] Ventiladores detenidos.")
    }

    fun ajustarTemperatura(grados: Int) {
        temperaturaC = grados
        println("[Clima] Termostato a $grados°C.")
    }
}

class DosificadorNutrientes {
    var mezclaPreparada = ""
        private set

    fun prepararMezcla(tipo: String) {
        mezclaPreparada = tipo
        println("[Nutrientes] Mezcla '$tipo' preparada.")
    }

    fun inyectarEnRiego() = println("[Nutrientes] Inyectando mezcla en el flujo de agua.")
}

/**
 * --- FACHADA (FACADE) ---
 * Su objetivo es unificar todos los sistemas
 * anteriores en comandos simples y humanos.
 */
class GestorHuertoFacade(
    private val riego: SistemaRiego = SistemaRiego(),
    private val luces: IluminacionLED = IluminacionLED(),
    private val clima: ControlClima = ControlClima(),
    private val nutrientes: DosificadorNutrientes = DosificadorNutrientes()
) {
    /**
     * Comandos que coordinan múltiples subsistemas.
     */

    fun activarModoCrecimientoAcelerado() {
        println("\n>>> ACTIVANDO MODO: CRECIMIENTO ACELERADO <<<")
        luces.encender()
        luces.ajustarEspectro("VEGETATIVO")
        luces.setIntensidad(100)
        clima.ajustarTemperatura(24)
        clima.activarVentiladores()
        nutrientes.prepararMezcla("NITRÓGENO ALTO")
        nutrientes.inyectarEnRiego()
        riego.establecerPresion(40)
        riego.abrirValvulas()
        println(">>> SISTEMA LISTO Y OPTIMIZADO <<<\n")
    }

    fun activarModoAhorroEnergia() {
        println("\n>>> ACTIVANDO MODO: AHORRO DE ENERGÍA <<<")
        luces.setIntensidad(30)
        clima.desactivarVentiladores()
        clima.ajustarTemperatura(18)
        riego.cerrarValvulas()
        println(">>> SISTEMA EN MODO REPOSO <<<\n")
    }

    fun prepararParaCosecha() {
        println("\n>>> PREPARANDO PARA COSECHA <<<")
        luces.ajustarEspectro("REPOSO")
        clima.ajustarTemperatura(20)
        riego.cerrarValvulas()
        println(">>> CONDICIONES IDEALES PARA COSECHAR <<<\n")
    }
}
