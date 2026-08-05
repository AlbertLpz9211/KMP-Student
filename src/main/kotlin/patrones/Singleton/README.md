Patrón Singleton (Instancia Única)
Problema
Ciertos recursos a nivel de aplicación (como conexiones a bases de datos, gestores de configuración o pool de hilos) deben tener exactamente una única instancia compartida globalmente. Si diferentes componentes instancian sus propias conexiones, se desperdician recursos del sistema y pueden surgir inconsistencias de estado concurrentes.

Solución
El patrón Singleton garantiza que una clase tenga solo una instancia y proporciona un punto de acceso global a ella. Oculta el constructor (o lo hace privado) e implementa un mecanismo para inicializar la instancia la primera vez que es requerida, devolviendo la misma referencia en llamadas subsecuentes.

Diagrama de Clases
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
Declaración object: En Kotlin, el patrón Singleton está integrado nativamente mediante la palabra clave object. 
El lenguaje gestiona la inicialización diferida (lazy) de forma segura ante hilos (thread-safe) sin necesidad de bloques
sincronizados manuales.

Cuándo NO usarlo
Dificultad para pruebas unitarias: Los Singletons globales actúan como estado global implícito, lo que complica el 
aislamiento durante las pruebas y dificulta la sustitución por mocks/stubs.
Violación del Principio de Responsabilidad Única: La clase gestiona su lógica de negocio y además el control de su ciclo 
de vida y concurrencia.

Patrones Relacionados
Abstract Factory, Builder, Prototype: A menudo, las implementaciones de estos patrones se estructuran como un Singleton.