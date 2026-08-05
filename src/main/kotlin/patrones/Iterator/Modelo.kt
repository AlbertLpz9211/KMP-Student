package patrones.iterator

// Esta interfaz define el comportamiento que debe tener cualquier iterador.
// Su objetivo es permitir recorrer una colección elemento por elemento.
interface Iterador<T> {

    // Verifica si todavía existen elementos por recorrer.
    // Devuelve true si aún hay elementos y false cuando ya no quedan.
    fun tieneSiguiente(): Boolean

    // Devuelve el siguiente elemento de la colección.
    fun siguiente(): T
}

// Esta clase implementa el iterador para recorrer una lista de nombres.
// Implementa la interfaz Iterador, por lo que debe definir
// los métodos tieneSiguiente() y siguiente().
class IteradorNombres(private val nombres: List<String>) : Iterador<String> {

    // Esta variable guarda la posición actual del recorrido.
    // Comienza en 0 porque el primer elemento de una lista
    // siempre se encuentra en esa posición.
    private var posicion = 0

    // Comprueba si todavía quedan elementos por recorrer.
    // Mientras la posición sea menor que el tamaño de la lista,
    // significa que aún existen elementos disponibles.
    override fun tieneSiguiente(): Boolean {
        return posicion < nombres.size
    }

    // Devuelve el siguiente elemento de la colección.
    override fun siguiente(): String {

        // Antes de obtener un elemento, verificamos que todavía exista.
        // Si ya no hay más elementos, se lanza una excepción para evitar errores.
        if (!tieneSiguiente()) {
            throw NoSuchElementException("No hay más elementos en la colección")
        }

        // Devuelve el elemento que está en la posición actual.
        // El operador ++ incrementa la posición después de devolver el valor,
        // para que la próxima vez se obtenga el siguiente elemento.
        return nombres[posicion++]
    }
}

// Esta clase representa la colección de nombres.
// Su función es almacenar la lista y proporcionar un iterador
// para recorrerla.
class ColeccionNombres(private val lista: List<String>) {

    // Crea y devuelve un nuevo iterador para recorrer la colección.
    // De esta manera, el usuario no necesita acceder directamente
    // a la lista interna.
    fun crearIterador(): Iterador<String> {
        return IteradorNombres(lista)
    }
}