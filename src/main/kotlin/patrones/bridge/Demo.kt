package patrones.bridge

fun main() {
    println("--- BIENVENIDO AL SISTEMA DE PUENTES HARDWARE-CONTROL ---")

    val riego = RiegoMecanico()
    val luces = IluminacionLEDHardware()

    println("\n--- COMBINACIÓN 1: Control de Pared operando el Sistema de Riego ---")
    val controlRiegoPared = ControlDePared(riego)
    controlRiegoPared.presionarBotonEncendido()
    controlRiegoPared.ejecutarComandoEspecial()
    controlRiegoPared.presionarBotonEncendido()

    println("\n--- COMBINACIÓN 2: Panel Automatizado operando la Iluminación LED ---")
    val panelLucesAvanzado = PanelAutomatizado(luces)
    panelLucesAvanzado.presionarBotonEncendido()
    panelLucesAvanzado.ejecutarComandoEspecial()

    println("\n--- DEMOSTRACIÓN BRIDGE FINALIZADA ---")
}