package patrones.flyweight

import kotlin.random.Random

/**
 * DEMOSTRACIÓN DEL PATRÓN FLYWEIGHT
 * Simulamos la creación de 10,000 soldados. Sin el patrón, tendríamos 10,000 texturas pesadas.
 * Con el patrón, solo tenemos 3 texturas compartidas.
 */
fun main() {
    println("--- SIMULADOR DE BATALLA (FLYWEIGHT) ---")

    val ejercito = mutableListOf<Soldado>()
    val nombresTipos = listOf("Arquero", "Guerrero", "Mago")
    val texturas = listOf("TEXTURA_ARCO_4K", "TEXTURA_ESPADA_4K", "TEXTURA_MAGO_4K")

    // Creamos 10,000 soldados aleatorios
    println("Reclutando 10,000 unidades...")
    
    repeat(10_000) { i ->
        val randomIdx = Random.nextInt(nombresTipos.size)
        
        // La fábrica nos da el objeto compartido (si ya existe, no crea uno nuevo)
        val tipoCompartido = FabricaUnidades.getTipo(
            nombre = nombresTipos[randomIdx],
            stats = "Ataque: ${Random.nextInt(10, 20)}",
            textura = texturas[randomIdx]
        )
        
        // El soldado guarda su identificador, posición y referencia al tipo
        ejercito.add(Soldado(
            id = i,
            tipo = tipoCompartido,
            x = Random.nextInt(0, 1000),
            y = Random.nextInt(0, 1000)
        ))
    }

    println("\n--- ESTADO DE LA MEMORIA ---")
    println("Total de soldados en el ejército: ${ejercito.size}")
    println("Total de objetos pesados (TipoUnidad) creados: ${FabricaUnidades.totalTiposCreados()}")
    
    // Mostramos una pequeña muestra
    println("\nVisualizando muestra del ejército:")
    ejercito.take(5).forEach { it.dibujar() }


    println("\n--- DEMOSTRACIÓN FINALIZADA ---")
}
