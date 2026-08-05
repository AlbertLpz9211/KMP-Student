package patrones.singleton

// Función principal del programa.
// Desde aquí comienza la ejecución de la demostración del patrón Singleton.
fun main() {

    // Imprime un título para indicar qué patrón de diseño se va a demostrar.
    println("--- DEMOSTRACIÓN PATRÓN SINGLETON ---")

    // ==========================================================
    // PASO 1: Obtener la primera referencia del Singleton
    // ==========================================================
    println("\n--- 1. Obteniendo primera referencia a la conexión ---")

    // Se obtiene la única instancia de la clase ConexionBaseDatos.
    // Como es un Singleton, no se crea un objeto nuevo,
    // sino que se devuelve siempre el mismo.
    val conexionA = ConexionBaseDatos.instancia

    // Se llama al método conectar() para simular una conexión
    // a la base de datos y mostrar un mensaje.
    println("Conexión A: ${conexionA.conectar()}")

    // ==========================================================
    // PASO 2: Obtener nuevamente la instancia del Singleton
    // ==========================================================
    println("\n--- 2. Obteniendo segunda referencia a la conexión ---")

    // Se vuelve a solicitar la instancia.
    // Aunque parezca que estamos creando otra variable,
    // realmente ambas apuntan al mismo objeto.
    val conexionB = ConexionBaseDatos.instancia

    // Se vuelve a llamar al método conectar().
    println("Conexión B: ${conexionB.conectar()}")

    // El operador === compara si ambas variables apuntan
    // exactamente al mismo objeto en memoria.
    //
    // Si el resultado es true, significa que solo existe
    // una única instancia de la clase, que es justamente
    // el objetivo del patrón Singleton.
    println("\n¿Ambas referencias son exactamente idénticas? ${conexionA === conexionB}")

    // Mensaje para indicar que terminó la demostración.
    println("\n--- DEMOSTRACIÓN SINGLETON FINALIZADA ---")
}