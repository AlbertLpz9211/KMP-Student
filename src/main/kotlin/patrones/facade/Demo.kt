package patrones.facade

/**
 * DEMOSTRACIÓN DEL PATRÓN FACADE
 * Aquí el 'Cliente' interactúa únicamente con la Fachada, sin saber
 * que existen 4 subsistemas complejos por debajo.
 */
fun main() {
    println("--- BIENVENIDO AL GESTOR DEL HUERTO INTELIGENTE ---")

    // Instanciamos la fachada (ella se encarga de crear los subsistemas por defecto)
    val gestor = GestorHuertoFacade()

    // El cliente solo llama a un método humano y descriptivo
    gestor.activarModoCrecimientoAcelerado()

    // Si las condiciones cambian, usamos otro comando simple
    gestor.activarModoAhorroEnergia()

    // O preparamos el ambiente para la cosecha
    gestor.prepararParaCosecha()

    println("--- DEMOSTRACIÓN FINALIZADA ---")
}
