package patrones.abstractfactory

// Función principal donde demostramos el funcionamiento del patrón Abstract Factory
fun main() {
    println("--- DEMOSTRACIÓN PATRÓN ABSTRACT FACTORY ---")

    // --- 1. Renderizando UI Tema Oscuro ---
    // Instanciamos la fábrica específica para el tema oscuro.
    // Usamos el tipo general (UIFactory) para aplicar el polimorfismo.
    val factoryOscura: UIFactory = OscuroUIFactory()

    // Le pedimos a la fábrica que cree la familia completa de componentes oscuros.
    val botonOscuro = factoryOscura.crearBoton()
    val ventanaOscura = factoryOscura.crearVentana()

    // Mostramos que ambos componentes pertenecen de forma coherente al tema oscuro.
    println("Componentes creados: ${botonOscuro.estilo} y ${ventanaOscura.estilo}")

    // --- 2. Renderizando UI Tema Claro ---
    // Cambiamos la fábrica concreta a la del tema claro sin modificar la forma de interactuar con ella.
    val factoryClara: UIFactory = ClaroUIFactory()

    // Solicitamos la creación de la familia de componentes claros.
    val botonClaro = factoryClara.crearBoton()
    val ventanaClara = factoryClara.crearVentana()

    // Imprimimos el resultado de la familia de componentes del tema claro.
    println("Componentes creados: ${botonClaro.estilo} y ${ventanaClara.estilo}")

    println("\n--- DEMOSTRACIÓN ABSTRACT FACTORY FINALIZADA ---")
}