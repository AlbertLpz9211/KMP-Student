package patrones.bridge

fun main() {
    println("--- BIENVENIDO AL REPRODUCTOR MULTIMEDIA (BRIDGE) ---")

    val altavozFisico = MotorAudioAltavoz()
    val audifonosBT = MotorAudioBluetooth()

    println("\n--- COMBINACIÓN 1: Interfaz Minimalista con Altavoz Físico ---")
    val playerMinimal = InterfazMinimalista(altavozFisico)
    playerMinimal.reproducirPista("cancion_rock.mp3")
    playerMinimal.ajustarAmbienteSonoro(30)

    println("\n--- COMBINACIÓN 2: Interfaz Avanzada con Audífonos Bluetooth ---")
    val playerPro = InterfazAvanzadaEcualizada(audifonosBT)
    playerPro.reproducirPista("podcast_tech.wav")
    playerPro.ajustarAmbienteSonoro(70)

    println("\n--- DEMOSTRACIÓN BRIDGE FINALIZADA ---")
}