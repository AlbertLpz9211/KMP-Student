package patrones.factorymethod

// Función principal donde probamos el patrón Factory Method
fun main() {
    println("--- DEMOSTRACIÓN PATRÓN FACTORY METHOD ---")

    // --- 1. Logística Digital ---
    // Instanciamos el creador específico para la logística digital.
    // Usamos el tipo general (CreadorLogistica) para aplicar el polimorfismo.
    val creadorDigital: CreadorLogistica = CreadorDigital()

    // Llamamos al método que ejecuta la lógica de negocio.
    // Internamente, este método crea el producto correspondiente y llama a sus funciones.
    println(creadorDigital.planificarEntrega())

    // --- 2. Logística Física ---
    // Hacemos lo mismo pero para la logística física.
    // Creamos la instancia del creador físico sin cambiar la forma en que interactuamos con él.
    val creadorFisico: CreadorLogistica = CreadorFisico()

    // Ejecutamos la planificación de entrega física.
    println(creadorFisico.planificarEntrega())

    println("\n--- DEMOSTRACIÓN FACTORY METHOD FINALIZADA ---")
}