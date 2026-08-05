package patrones.abstractfactory

// 1. INTERFACES DE LOS PRODUCTOS INDIVIDUALES
// Definen la estructura base de cada componente visual.
interface Boton {
    val estilo: String
}

interface Ventana {
    val estilo: String
}

// 2. PRODUCTOS CONCRETOS (Familia Oscura)
// Implementación del botón para el tema oscuro.
class BotonOscuro : Boton {
    override val estilo: String = "BotonOscuro"
}

// Implementación de la ventana para el tema oscuro.
class VentanaOscura : Ventana {
    override val estilo: String = "VentanaOscura"
}

// 3. PRODUCTOS CONCRETOS (Familia Clara)
// Implementación del botón para el tema claro.
class BotonClaro : Boton {
    override val estilo: String = "BotonClaro"
}

// Implementación de la ventana para el tema claro.
class VentanaClara : Ventana {
    override val estilo: String = "VentanaClara"
}

// 4. FABRICA ABSTRACTA (Abstract Factory)
// Declara los métodos de fabricación para CADA producto que conforma la familia de componentes visuales.
interface UIFactory {
    fun crearBoton(): Boton
    fun crearVentana(): Ventana
}

// 5. FABRICAS CONCRETAS
// Fabrica concreta encargada de construir EXCLUSIVAMENTE la familia de componentes con tema oscuro.
class OscuroUIFactory : UIFactory {
    override fun crearBoton(): Boton = BotonOscuro()
    override fun crearVentana(): Ventana = VentanaOscura()
}

// Fabrica concreta encargada de construir EXCLUSIVAMENTE la familia de componentes con tema claro.
class ClaroUIFactory : UIFactory {
    override fun crearBoton(): Boton = BotonClaro()
    override fun crearVentana(): Ventana = VentanaClara()
}