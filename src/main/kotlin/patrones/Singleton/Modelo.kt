package patrones.singleton

// Esta clase representa una conexión a una base de datos.
// Se implementa utilizando el patrón Singleton para asegurar
// que solo exista una única instancia durante toda la ejecución del programa.
class ConexionBaseDatos private constructor() {

    // El constructor es privado.
    // Esto evita que otras clases puedan crear objetos usando:
    // ConexionBaseDatos()
    // De esta manera se controla que solo exista una instancia.

    // Este método simula que se establece una conexión
    // con la base de datos y devuelve un mensaje.
    fun conectar(): String = "Conexión establecida"

    // El companion object funciona como una parte estática de la clase.
    // Aquí se guarda la única instancia del Singleton.
    companion object {

        // La propiedad "instancia" contiene el único objeto
        // de la clase ConexionBaseDatos.
        //
        // by lazy significa que el objeto no se crea al iniciar
        // el programa, sino hasta la primera vez que alguien lo solicita.
        //
        // Después de crearse, siempre se devolverá esa misma instancia.
        val instancia: ConexionBaseDatos by lazy {
            ConexionBaseDatos()
        }
    }
}