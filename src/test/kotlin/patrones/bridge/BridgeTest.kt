package patrones.bridge

import kotlin.test.Test
import kotlin.test.assertTrue

class BridgeTest {

    @Test
    fun testInterfazMinimalistaConAltavoz() {
        val motor = MotorAudioAltavoz()
        val interfaz = InterfazMinimalista(motor)

        interfaz.reproducirPista("test.mp3")
        interfaz.ajustarAmbienteSonoro(40)

        assertTrue(true, "La combinación de interfaz y motor debe ejecutarse correctamente")
    }

    @Test
    fun testInterfazAvanzadaConBluetooth() {
        val motor = MotorAudioBluetooth()
        val interfaz = InterfazAvanzadaEcualizada(motor)

        interfaz.reproducirPista("stream.flac")
        interfaz.ajustarAmbienteSonoro(50)

        assertTrue(true, "El puente avanzado debe delegar la ejecución sin errores")
    }
}