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