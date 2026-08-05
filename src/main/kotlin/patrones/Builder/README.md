Patrón Builder (Constructor)
Problema
Al construir objetos complejos como configuraciones de reportes o entidades de dominio con docenas de campos 
opcionales (ej. título, subtítulo, inclusión de gráficos, tablas de datos, encabezados, etc.), los constructores 
estándar sufren del antipatrón de "constructor telescópico" (múltiples sobrecargas del constructor). 
Esto provoca confusión en el orden de parámetros, paso de valores null innecesarios y baja legibilidad.

Solución
El patrón Builder separa la construcción de un objeto complejo de su representación, permitiendo crear diferentes
configuraciones de un objeto paso a paso mediante una API fluida (method chaining). Un método final (build()) 
valida y retorna el objeto completamente construido e inmutable.

Diagrama de Clases
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
Parámetros con nombre y valores por defecto: En Kotlin, las data classes con valores predeterminados reducen 
drásticamente la necesidad del patrón Builder tradicional. Sin embargo, para construcciones en múltiples etapas 
o validaciones en varios pasos, el Builder se implementa utilizando funciones receptoras con DSLs de Kotlin 
(ej. buildReporte { ... }).

Cuándo NO usarlo
Objetos simples: Si el objeto tiene pocos parámetros obligatorios u opcionales, basta con un constructor estándar
o los argumentos con nombre de Kotlin.
Estructuras mutables pequeñas: Si el estado del objeto se puede modificar de forma segura mediante propiedades 
simples sin restricciones de inmutabilidad.

Patrones Relacionados
Abstract Factory: Puede interactuar con Builder cuando los productos a crear son estructuras complejas compuestas
de múltiples partes.
Composite: Frecuentemente los objetos complejos construidos mediante un Builder adoptan la estructura de un árbol
Composite.