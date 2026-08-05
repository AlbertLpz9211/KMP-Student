Patrón Factory Method (Método Fábrica)
Problema
En una aplicación de logística, inicialmente solo procesamos entregas por carretera (TransporteCamion). 
A medida que la aplicación crece, surge la necesidad de agregar entregas marítimas (TransporteBarco) y 
digitales (TransporteDigital). Si acoplamos directamente la creación de las instancias en el código cliente 
mediante new o constructores directos con condicionales, agregando nuevos tipos de transporte requerirá 
modificar el código existente en múltiples puntos, violando el principio de abierto/cerrado 
(Open/Closed Principle).

Solución
El patrón Factory Method sugiere reemplazar las llamadas directas de construcción de objetos por 
llamadas a un método fábrica especial. Las subclases heredan de una clase creadora base y sobrescriben 
este método fábrica para instanciar y retornar tipos específicos de productos que implementan una 
interfaz común (ServicioEntrega).

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
Concrete Creator	CreadorDigital, CreadorFisico	Sobrescriben el método fábrica para devolver la instancia 
concreta correspondiente.
Kotlin Idiomático
Uso de interfaces y sealed classes: Facilita la exhaustividad al emparejar productos y creadores.
Funciones de orden superior o lambdas: En Kotlin, en muchos casos un Factory Method simple puede 
sustituirse o simplificarse pasando una función constructora () -> ServicioEntrega directamente.
Cuándo NO usarlo
Pocas variantes fijas: Si el conjunto de productos es pequeño y nunca cambia, introducir jerarquías de 
creadores agrega complejidad innecesaria.
Jerarquías pequeñas o sin cliente abstracto: Si el código cliente necesita conocer detalles concretos del 
producto resultante en lugar de la interfaz.
Patrones Relacionados
Abstract Factory: A menudo se implementa utilizando un conjunto de métodos fábrica.
Template Method: Los Factory Methods a menudo son invocados dentro de un algoritmo principal definido en un 
Template Method.