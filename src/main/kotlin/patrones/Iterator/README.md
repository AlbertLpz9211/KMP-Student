Patrón Iterator (Iterador)
Problema
Las colecciones de datos pueden almacenarse internamente en múltiples estructuras (listas, árboles, grafos, tablas hash). Si el código cliente necesita recorrer todos los elementos de la colección y conoce su estructura interna, se genera un alto acoplamiento. Además, exponer la estructura interna vulnera la encapsulación de la colección.

Solución
El patrón Iterator extrae el comportamiento de recorrido de una colección y lo coloca en un objeto independiente llamado iterador. El iterador encapsula detalles como la posición actual y los pasos restantes, ofreciendo una interfaz uniforme (tieneSiguiente(), siguiente()) para recorrer cualquier colección sin importar su representación interna.

Diagrama de Clases
classDiagram
  class Iterador~T~ {
    <<interface>>
    +tieneSiguiente(): Boolean
    +siguiente(): T
}
class IteradorNombres {
    -posicion: Int
    +tieneSiguiente(): Boolean
    +siguiente(): String
}
class ColeccionNombres {
    +crearIterador(): Iterador
}

    Iterador <|.. IteradorNombres
    ColeccionNombres ..> IteradorNombres : crea


Patrón Factory Method (Método Fábrica)
Problema
En una aplicación de logística, inicialmente solo procesamos entregas por carretera (TransporteCamion). A medida que la aplicación crece, surge la necesidad de agregar entregas marítimas (TransporteBarco) y digitales (TransporteDigital). Si acoplamos directamente la creación de las instancias en el código cliente mediante new o constructores directos con condicionales, agregando nuevos tipos de transporte requerirá modificar el código existente en múltiples puntos, violando el principio de abierto/cerrado (Open/Closed Principle).

Solución
El patrón Factory Method sugiere reemplazar las llamadas directas de construcción de objetos por llamadas a un método fábrica especial. Las subclases heredan de una clase creadora base y sobrescriben este método fábrica para instanciar y retornar tipos específicos de productos que implementan una interfaz común (ServicioEntrega).

Diagrama de Clases
Fragmento de código
classDiagram
class ServicioEntrega {
<<interface>>
+obtenerMetodo(): String
}
class EntregaDigital {
+obtenerMetodo(): String
}
class EntregaFisica {
+obtenerMetodo(): String
}
class CreadorLogistica {
<<abstract>>
+crearServicioEntrega()* ServicioEntrega
+planificarEntrega(): String
}
class CreadorDigital {
+crearServicioEntrega(): ServicioEntrega
}
class CreadorFisico {
+crearServicioEntrega(): ServicioEntrega
}

    ServicioEntrega <|.. EntregaDigital
    ServicioEntrega <|.. EntregaFisica
    CreadorLogistica <|-- CreadorDigital
    CreadorLogistica <|-- CreadorFisico
    CreadorDigital ..> EntregaDigital : crea
    CreadorFisico ..> EntregaFisica : crea
Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Product	ServicioEntrega	Define la interfaz para los objetos que el método fábrica crea.
Concrete Product	EntregaDigital, EntregaFisica	Implementaciones específicas de la interfaz de producto.
Creator	CreadorLogistica	Declara el método fábrica abstracto que devuelve objetos de tipo ServicioEntrega.
Concrete Creator	CreadorDigital, CreadorFisico	Sobrescriben el método fábrica para devolver la instancia concreta correspondiente.
Kotlin Idiomático
Uso de interfaces y sealed classes: Facilita la exhaustividad al emparejar productos y creadores.
Funciones de orden superior o lambdas: En Kotlin, en muchos casos un Factory Method simple puede sustituirse o simplificarse pasando una función constructora () -> ServicioEntrega directamente.
Cuándo NO usarlo
Pocas variantes fijas: Si el conjunto de productos es pequeño y nunca cambia, introducir jerarquías de creadores agrega complejidad innecesaria.
Jerarquías pequeñas o sin cliente abstracto: Si el código cliente necesita conocer detalles concretos del producto resultante en lugar de la interfaz.
Patrones Relacionados
Abstract Factory: A menudo se implementa utilizando un conjunto de métodos fábrica.
Template Method: Los Factory Methods a menudo son invocados dentro de un algoritmo principal definido en un Template Method.
Patrón Abstract Factory (Fábrica Abstracta)
Problema
En una aplicación multiplataforma de interfaz de usuario, necesitamos crear componentes visuales como Botones y Ventanas. Si el usuario selecciona el tema "Oscuro", todos los componentes deben pertenecer a la familia oscura; si selecciona "Claro", a la familia clara. Instanciar individualmente clases concretas en el código cliente arriesga mezclar elementos incompletos o incompatibles (ej. un BotonOscuro dentro de una VentanaClara).

Solución
El patrón Abstract Factory proporciona una interfaz para crear familias de objetos relacionados o dependientes sin especificar sus clases concretas. Cada fábrica concreta corresponde a una variante específica de la familia de productos (ej. OscuroUIFactory vs ClaroUIFactory) y garantiza que los productos obtenidos sean siempre compatibles entre sí.

Diagrama de Clases
Fragmento de código
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
Abstract Products	Boton, Ventana	Declaraciones de las interfaces para una familia de productos independientes pero relacionados.
Concrete Products	BotonOscuro, BotonClaro, VentanaOscura, etc.	Implementaciones concretas de cada producto agrupadas por familia.
Abstract Factory	UIFactory	Declara métodos para la creación de cada uno de los productos abstractos de la familia.
Concrete Factory	OscuroUIFactory, ClaroUIFactory	Implementan la interfaz creando instancias de la variante correspondiente.
Kotlin Idiomático
Inyección de Dependencias nativa: La fábrica se pasa mediante el constructor del cliente de forma concisa.
Módulos con object: Si una fábrica concreta no mantiene estado interno, se puede declarar como un object Singleton en Kotlin.
Cuándo NO usarlo
Nuevos tipos de productos frecuentes: Si se requiere agregar con frecuencia nuevos tipos de productos (ej. CheckBox, Slider), la interfaz de la fábrica base debe modificarse constantemente junto con todas sus implementaciones.
Familias de un solo producto: Para crear objetos aislados, un Factory Method es suficiente.
Patrones Relacionados
Factory Method: Las fábricas abstractas suelen componerse de varios Factory Methods.
Prototype: Las fábricas abstractas se pueden implementar clonando prototipos preconfigurados en lugar de instanciar clases directamente.
Patrón Builder (Constructor)
Problema
Al construir objetos complejos como configuraciones de reportes o entidades de dominio con docenas de campos opcionales (ej. título, subtítulo, inclusión de gráficos, tablas de datos, encabezados, etc.), los constructores estándar sufren del antipatrón de "constructor telescópico" (múltiples sobrecargas del constructor). Esto provoca confusión en el orden de parámetros, paso de valores null innecesarios y baja legibilidad.

Solución
El patrón Builder separa la construcción de un objeto complejo de su representación, permitiendo crear diferentes configuraciones de un objeto paso a paso mediante una API fluida (method chaining). Un método final (build()) valida y retorna el objeto completamente construido e inmutable.

Diagrama de Clases
Fragmento de código
classDiagram
class ReporteConfiguracion {
+titulo: String
+tieneGraficos: Boolean
+tieneTablaDatos: Boolean
}
class ReporteBuilder {
-titulo: String
-tieneGraficos: Boolean
-tieneTablaDatos: Boolean
+setTitulo(titulo: String): ReporteBuilder
+incluirGraficos(incluir: Boolean): ReporteBuilder
+incluirTablaDatos(incluir: Boolean): ReporteBuilder
+build(): ReporteConfiguracion
}

    ReporteBuilder ..> ReporteConfiguracion : construye
Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Product	ReporteConfiguracion	Objeto complejo resultante producido por la construcción.
Builder	ReporteBuilder	Define los pasos de configuración y el método de ensamblaje final build().
Client	BuilderTest.kt	Configura secuencialmente las opciones requeridas y solicita la creación del objeto.
Kotlin Idiomático
Parámetros con nombre y valores por defecto: En Kotlin, las data classes con valores predeterminados reducen drásticamente la necesidad del patrón Builder tradicional. Sin embargo, para construcciones en múltiples etapas o validaciones en varios pasos, el Builder se implementa utilizando funciones receptoras con DSLs de Kotlin (ej. buildReporte { ... }).
Cuándo NO usarlo
Objetos simples: Si el objeto tiene pocos parámetros obligatorios u opcionales, basta con un constructor estándar o los argumentos con nombre de Kotlin.
Estructuras mutables pequeñas: Si el estado del objeto se puede modificar de forma segura mediante propiedades simples sin restricciones de inmutabilidad.
Patrones Relacionados
Abstract Factory: Puede interactuar con Builder cuando los productos a crear son estructuras complejas compuestas de múltiples partes.
Composite: Frecuentemente los objetos complejos construidos mediante un Builder adoptan la estructura de un árbol Composite.
Patrón Prototype (Prototipo)
Problema
Crear copias exactas de un objeto existente no siempre es trivial usando instanciación normal. Esto ocurre si el objeto tiene campos privados, depende de consultas costosas a bases de datos/red para inicializar su estado, o si el código cliente solo conoce la interfaz abstracta del objeto y no su clase concreta.

Solución
El patrón Prototype delega el proceso de clonación a los propios objetos creados. El objeto declara una interfaz de clonación (clone()) que permite duplicar su estado actual campo por campo, creando una instancia independiente en memoria sin acoplar el cliente a su clase concreta.

Diagrama de Clases
Fragmento de código
classDiagram
class PrototipoDocumento {
<<interface>>
+clone(): PrototipoDocumento
}
class DocumentoBase {
+titulo: String
+paginas: Int
+clone(): DocumentoBase
}

    PrototipoDocumento <|.. DocumentoBase
Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Prototype	PrototipoDocumento	Interfaz que declara el método de clonación clone().
Concrete Prototype	DocumentoBase	Objeto que implementa el método de clonación duplica su propio estado.
Client	PrototypeTest.kt	Duplica objetos llamando al método de clonación sobre las instancias existentes.
Kotlin Idiomático
Data Classes y método .copy(): Kotlin incluye soporte de prototipado nativo mediante el método autogenerado .copy() en las data classes, permitiendo duplicar un objeto y opcionalmente sobrescribir propiedades en una sola instrucción.
Cuándo NO usarlo
Objetos con referencias circulares complejas: Clonar objetos con referencias cruzadas o grafos circulares puede derivar en desbordamientos de pila o copias profundas extremadamente complejas.
Clases con estados sencillos e inmutables: Si crear una instancia nueva con el constructor es rápido y barato, la clonación no aporta valor.
Patrones Relacionados
Abstract Factory: Puede almacenar un conjunto de prototipos para clonar y retornar productos de la fábrica.
Memento: Puede usarse como alternativa si se desea guardar estados históricos del objeto en lugar de duplicar la instancia para uso activo.
Patrón Singleton (Instancia Única)
Problema
Ciertos recursos a nivel de aplicación (como conexiones a bases de datos, gestores de configuración o pool de hilos) deben tener exactamente una única instancia compartida globalmente. Si diferentes componentes instancian sus propias conexiones, se desperdician recursos del sistema y pueden surgir inconsistencias de estado concurrentes.

Solución
El patrón Singleton garantiza que una clase tenga solo una instancia y proporciona un punto de acceso global a ella. Oculta el constructor (o lo hace privado) e implementa un mecanismo para inicializar la instancia la primera vez que es requerida, devolviendo la misma referencia en llamadas subsecuentes.

Diagrama de Clases
Fragmento de código
classDiagram
class ConexionBaseDatos {
-instancia: ConexionBaseDatos
-ConexionBaseDatos()
+getInstancia(): ConexionBaseDatos
}
Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Singleton	ConexionBaseDatos	Clase que gestiona su propia instancia única global mediante un acceso controlado.
Client	SingletonTest.kt	Accede a la instancia compartida sin crear nuevos objetos.
Kotlin Idiomático
Declaración object: En Kotlin, el patrón Singleton está integrado nativamente mediante la palabra clave object. El lenguaje gestiona la inicialización diferida (lazy) de forma segura ante hilos (thread-safe) sin necesidad de bloques sincronizados manuales.
Cuándo NO usarlo
Dificultad para pruebas unitarias: Los Singletons globales actúan como estado global implícito, lo que complica el aislamiento durante las pruebas y dificulta la sustitución por mocks/stubs.
Violación del Principio de Responsabilidad Única: La clase gestiona su lógica de negocio y además el control de su ciclo de vida y concurrencia.
Patrones Relacionados
Abstract Factory, Builder, Prototype: A menudo, las implementaciones de estos patrones se estructuran como un Singleton.
Patrón Iterator (Iterador)
Problema
Las colecciones de datos pueden almacenarse internamente en múltiples estructuras (listas, árboles, grafos, tablas hash). Si el código cliente necesita recorrer todos los elementos de la colección y conoce su estructura interna, se genera un alto acoplamiento. Además, exponer la estructura interna vulnera la encapsulación de la colección.

Solución
El patrón Iterator extrae el comportamiento de recorrido de una colección y lo coloca en un objeto independiente llamado iterador. El iterador encapsula detalles como la posición actual y los pasos restantes, ofreciendo una interfaz uniforme (tieneSiguiente(), siguiente()) para recorrer cualquier colección sin importar su representación interna.

Diagrama de Clases
Fragmento de código
classDiagram
class Iterador~T~ {
<<interface>>
+tieneSiguiente(): Boolean
+siguiente(): T
}
class IteradorNombres {
-posicion: Int
+tieneSiguiente(): Boolean
+siguiente(): String
}
class ColeccionNombres {
+crearIterador(): Iterador
}

    Iterador <|.. IteradorNombres
    ColeccionNombres ..> IteradorNombres : crea
Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Iterator	Iterador	Define la interfaz para acceder y recorrer los elementos.
Concrete Iterator	IteradorNombres	Implementa el algoritmo de recorrido manteniendo el estado actual de lectura.
Aggregate	ColeccionNombres	Estructura de datos que declara el método para instanciar el iterador 
correspondiente.

Kotlin Idiomático
Operador iterator() y Sequence: Kotlin permite implementar el operador operator fun iterator() para permitir 
el uso directo del bucle for (item in coleccion). Asimismo, las Sequence de Kotlin ofrecen evaluación perezosa 
(lazy evaluation) de iteraciones.

Cuándo NO usarlo
Colecciones simples: Si la aplicación solo utiliza listas sencillas que raramente cambian, abstraer el 
recorrido agrega clases innecesarias.
Estructuras que ya ofrecen iteración nativa eficiente: No se requiere reimplementar iteradores cuando las 
colecciones del lenguaje ya cubren todos los casos.

Patrones Relacionados
Composite: Los iteradores se emplean frecuentemente para recorrer estructuras compuestas complejas (árboles).
Factory Method: La creación del iterador adecuado dentro de la colección suele delegarse mediante un Factory Method.
