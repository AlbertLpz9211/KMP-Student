Patrón Abstract Factory (Fábrica Abstracta)
Problema
En una aplicación multiplataforma de interfaz de usuario, necesitamos crear componentes visuales como Botones y 
Ventanas. Si el usuario selecciona el tema "Oscuro", todos los componentes deben pertenecer a la familia oscura; 
si selecciona "Claro", a la familia clara. Instanciar individualmente clases concretas en el código cliente 
arriesga mezclar elementos incompletos o incompatibles (ej. un BotonOscuro dentro de una VentanaClara).

Solución
El patrón Abstract Factory proporciona una interfaz para crear familias de objetos relacionados o dependientes 
sin especificar sus clases concretas. Cada fábrica concreta corresponde a una variante específica de la familia 
de productos (ej. OscuroUIFactory vs ClaroUIFactory) y garantiza que los productos obtenidos sean siempre 
compatibles entre sí.

classDiagram
class Boton {
<<interface>>
+estilo: String
}
class Ventana {
<<interface>>
+estilo: String
}
class BotonOscuro { +estilo: String }
class BotonClaro { +estilo: String }
class VentanaOscura { +estilo: String }
class VentanaClara { +estilo: String }

    class UIFactory {
        <<interface>>
        +crearBoton(): Boton
        +crearVentana(): Ventana
    }
    class OscuroUIFactory {
        +crearBoton(): Boton
        +crearVentana(): Ventana
    }
    class ClaroUIFactory {
        +crearBoton(): Boton
        +crearVentana(): Ventana
    }

    Boton <|.. BotonOscuro
    Boton <|.. BotonClaro
    Ventana <|.. VentanaOscura
    Ventana <|.. VentanaClara

    UIFactory <|.. OscuroUIFactory
    UIFactory <|.. ClaroUIFactory
    OscuroUIFactory ..> BotonOscuro : crea
    OscuroUIFactory ..> VentanaOscura : crea
    ClaroUIFactory ..> BotonClaro : crea
    ClaroUIFactory ..> VentanaClara : crea

Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Abstract Products	Boton, Ventana	Declaraciones de las interfaces para una familia de productos independientes 
pero relacionados.
Concrete Products	BotonOscuro, BotonClaro, VentanaOscura, etc.	Implementaciones concretas de cada producto 
agrupadas por familia.
Abstract Factory	UIFactory	Declara métodos para la creación de cada uno de los productos abstractos de la 
familia.

Concrete Factory	OscuroUIFactory, ClaroUIFactory	Implementan la interfaz creando instancias de la variante 
correspondiente.

Kotlin Idiomático
Inyección de Dependencias nativa: La fábrica se pasa mediante el constructor del cliente de forma concisa.
Módulos con object: Si una fábrica concreta no mantiene estado interno, se puede declarar como un object 
Singleton en Kotlin.

Cuándo NO usarlo
Nuevos tipos de productos frecuentes: Si se requiere agregar con frecuencia nuevos tipos de productos 
(ej. CheckBox, Slider), la interfaz de la fábrica base debe modificarse constantemente junto con todas sus 
implementaciones.
Familias de un solo producto: Para crear objetos aislados, un Factory Method es suficiente.

Patrones Relacionados
Factory Method: Las fábricas abstractas suelen componerse de varios Factory Methods.
Prototype: Las fábricas abstractas se pueden implementar clonando prototipos preconfigurados en lugar de 
instanciar clases directamente.