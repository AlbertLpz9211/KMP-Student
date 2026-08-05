package patrones.abstractfactory

import kotlin.test.Test
import kotlin.test.assertTrue

// CLASE DE PRUEBAS UNITARIAS (PATRÓN ABSTRACT FACTORY)
// En esta clase validamos que cada fábrica cree correctamente su FAMILIA completa de objetos
// y que todos los componentes sean coherentes entre sí (mismo tema visual).
class AbstractFactoryTest {

    // @Test indica al framework de pruebas que este método es un test ejecutable.
    @Test
    fun testFamiliaUIOscura() {
        // ARRANGE:
        // Instanciamos la fábrica concreta del tema oscuro usando la interfaz general (UIFactory).
        val factory: UIFactory = OscuroUIFactory()

        // ACT:
        // Le pedimos a la fábrica que cree sus componentes individuales (botón y ventana).
        val boton = factory.crearBoton()
        val ventana = factory.crearVentana()

        // ASSERT:
        // Comprobamos que AMBOS componentes pertenezcan a la misma familia visual ("Oscuro").
        // Esto confirma que Abstract Factory mantiene la coherencia entre productos relacionados.
        assertTrue(boton.estilo == "BotonOscuro" && ventana.estilo == "VentanaOscura")
    }

    // Segunda prueba automatizada para la familia del tema claro.
    @Test
    fun testFamiliaUIClara() {
        // ARRANGE:
        // Instanciamos la fábrica concreta del tema claro.
        val factory: UIFactory = ClaroUIFactory()

        // ACT:
        // Solicitamos a la fábrica la creación de los componentes correspondientes.
        val boton = factory.crearBoton()
        val ventana = factory.crearVentana()

        // ASSERT:
        // Validamos que tanto el botón como la ventana tengan el estilo "Claro".
        assertTrue(boton.estilo == "BotonClaro" && ventana.estilo == "VentanaClara")
    }
}