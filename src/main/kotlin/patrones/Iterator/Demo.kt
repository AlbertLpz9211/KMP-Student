package patrones.iterator

// Función principal del programa.
// Desde aquí comienza la demostración del patrón Iterator.
fun main() {

    // Imprime un título para indicar qué patrón de diseño se va a demostrar.
    println("--- DEMOSTRACIÓN PATRÓN ITERATOR ---")

    // Se crea una lista con varios nombres de usuarios.
    // Esta será la colección que vamos a recorrer.
    val listaUsuarios = listOf("Ana", "Carlos", "Beatriz", "David")

    // Se crea un objeto de la clase ColeccionNombres,
    // enviándole la lista de usuarios como parámetro.
    val coleccion = ColeccionNombres(listaUsuarios)

    // Se obtiene un iterador de la colección.
    // El iterador será el encargado de recorrer los elementos
    // uno por uno, sin acceder directamente a la lista.
    val iterador = coleccion.crearIterador()

    println("\n--- Recorriendo la colección secuencialmente ---")

    // Variable que servirá para numerar cada usuario al imprimirlo.
    var contador = 1

    // Mientras existan más elementos en la colección,
    // el ciclo seguirá ejecutándose.
    while (iterador.tieneSiguiente()) {

        // Obtiene el siguiente elemento de la colección.
        val nombre = iterador.siguiente()

        // Imprime el número del usuario y su nombre.
        println("Usuario $contador: $nombre")

        // Aumenta el contador para el siguiente usuario.
        contador++
    }

    // Mensaje para indicar que terminó la demostración.
    println("\n--- DEMOSTRACIÓN ITERATOR FINALIZADA ---")
}