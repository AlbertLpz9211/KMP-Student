Patrón Prototype (Prototipo)
Problema
Crear copias exactas de un objeto existente no siempre es trivial usando instanciación normal. Esto ocurre si 
el objeto tiene campos privados, depende de consultas costosas a bases de datos/red para inicializar su estado,
o si el código cliente solo conoce la interfaz abstracta del objeto y no su clase concreta.

Solución
El patrón Prototype delega el proceso de clonación a los propios objetos creados. El objeto declara una interfaz
de clonación (clone()) que permite duplicar su estado actual campo por campo, creando una instancia independiente
en memoria sin acoplar el cliente a su clase concreta.

Diagrama de Clases
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
Data Classes y método .copy(): Kotlin incluye soporte de prototipado nativo mediante el método 
autogenerado .copy() en las data classes, permitiendo duplicar un objeto y opcionalmente sobrescribir propiedades
en una sola instrucción.

Cuándo NO usarlo
Objetos con referencias circulares complejas: Clonar objetos con referencias cruzadas o grafos circulares puede
derivar en desbordamientos de pila o copias profundas extremadamente complejas.
Clases con estados sencillos e inmutables: Si crear una instancia nueva con el constructor es rápido y barato, 
la clonación no aporta valor.

Patrones Relacionados
Abstract Factory: Puede almacenar un conjunto de prototipos para clonar y retornar productos de la fábrica.
Memento: Puede usarse como alternativa si se desea guardar estados históricos del objeto en lugar de duplicar 
la instancia para uso activo.