Patrón Template Method (Método Plantilla)
Problema
Al procesar diferentes formatos de documentos (PDF, Excel, CSV), el esqueleto del algoritmo de procesamiento suele ser idéntico: abrir archivo, extraer contenido, analizar datos, generar reporte y cerrar archivo. Si cada clase de documento reimplementa todo el algoritmo, se duplica el flujo estructural y resulta complejo modificar el proceso global sin modificar todas las clases.

Solución
El patrón Template Method define el esqueleto de un algoritmo en un método de la clase base, delegando la implementación de ciertos pasos específicos a las subclases. Esto permite a las subclases redefinir pasos individuales sin alterar la estructura general ni el orden de ejecución del algoritmo.

Diagrama de Clases
classDiagram
class ProcesadorDocumento {
<<abstract>>
+procesar(): Boolean
#abrirArchivo()*
#extraerContenido()*
#cerrarArchivo()
}
class ProcesadorPDF {
#abrirArchivo()
#extraerContenido()
}
class ProcesadorExcel {
#abrirArchivo()
#extraerContenido()
}

ProcesadorDocumento <|-- ProcesadorPDF
ProcesadorDocumento <|-- ProcesadorExcel

Participantes
Rol del Patrón	Clase/Interfaz en el Código	Descripción
Abstract Class	ProcesadorDocumento	Define el método plantilla (procesar()) con los pasos del algoritmo y declara métodos abstractos/ganchos (hooks).
Concrete Class	ProcesadorPDF, ProcesadorExcel	Implementan los pasos abstractos o ganchos específicos requeridos por el método plantilla.
Kotlin Idiomático
Paso de Lambdas y Funciones Receptoras: En Kotlin, en lugar de crear una jerarquía de clases con herencia, un Template Method puede implementarse pasando funciones como parámetros a un método de orden superior (ej. procesarDocumento(abrir = { ... }, extraer = { ... })).
Cuándo NO usarlo
Algoritmos con estructuras altamente cambiantes: Si el flujo de pasos varia significativamente entre variantes, la plantilla resulta rígida e inútil.
Riesgo de violar el Principio de Sustitución de Liskov: Si las subclases suprimen pasos fundamentales del algoritmo base lanzando excepciones o dejándolos vacíos.
Patrones Relacionados
Strategy: El Template Method se basa en la herencia para alterar partes de un algoritmo en tiempo de compilación; Strategy utiliza la composición para cambiar algoritmos enteros en tiempo de ejecución.
Factory Method: Los pasos específicos de un Template Method suelen ser llamadas a Factory Methods.