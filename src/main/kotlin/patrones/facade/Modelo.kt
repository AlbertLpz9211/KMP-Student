package patrones.factorymethod

// 1. INTERFAZ PRODUCTO (ServicioEntrega)
// Define el contrato general para todos los tipos de entrega que podemos tener.
// Todos los productos concretos deben implementar esta interfaz.
interface ServicioEntrega {
    fun obtenerMetodo(): String
}

// 2. PRODUCTOS CONCRETOS
// Representa la entrega en formato digital.
class EntregaDigital : ServicioEntrega {
    // Implementamos el método devolviendo la forma específica de entrega digital.
    override fun obtenerMetodo(): String = "Descarga Inmediata"
}

// Representa la entrega en formato físico.
class EntregaFisica : ServicioEntrega {
    // Implementamos el método devolviendo la forma específica de entrega física.
    override fun obtenerMetodo(): String = "Envío por Paquetería"
}

// 3. CLASE CREADORA ABSTRACTA
// Contiene la lógica general y declara el "Factory Method" (crearServicioEntrega).
abstract class CreadorLogistica {

    // Este es el FACTORY METHOD.
    // Es abstracto porque la clase base NO sabe qué producto concreto instanciar;
    // deja que las subclases decidan cuál crear.
    abstract fun crearServicioEntrega(): ServicioEntrega

    // Método con la lógica de negocio.
    // Trabaja con la interfaz general (ServicioEntrega) en lugar de clases concretas.
    fun planificarEntrega(): String {
        // Llama al Factory Method para obtener el producto correspondiente
        val servicio = crearServicioEntrega()
        // Ejecuta la operación sobre el producto sin preocuparse por cuál es en concreto
        return "Procesando: ${servicio.obtenerMetodo()}"
    }
}

// 4. CREADORES CONCRETOS
// Subclase encargada de fabricar la logística digital.
class CreadorDigital : CreadorLogistica() {
    // Sobrescribimos el Factory Method para retornar una instancia de EntregaDigital.
    override fun crearServicioEntrega(): ServicioEntrega {
        return EntregaDigital()
    }
}

// Subclase encargada de fabricar la logística física.
class CreadorFisico : CreadorLogistica() {
    // Sobrescribimos el Factory Method para retornar una instancia de EntregaFisica.
    override fun crearServicioEntrega(): ServicioEntrega {
        return EntregaFisica()
    }
}