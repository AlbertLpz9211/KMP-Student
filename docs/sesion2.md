# ¿Qué era freeze() y por qué desapareció?

El concepto de `freeze()` era el pilar (y el dolor de cabeza) del antiguo Modelo de Memoria de Kotlin/Native utilizado para iOS. En los inicios de Kotlin Multiplatform, la regla de seguridad para manejar asincronía y multihilos era estricta: un objeto solo podía ser modificado por el hilo que lo creó. Si deseabas compartir ese objeto con un hilo secundario (por ejemplo, enviar datos de la red hacia la interfaz de usuario en iOS), estabas obligado a "congelarlo" utilizando el comando `freeze()`.

Congelar un objeto significaba volverlo absoluta y permanentemente inmutable. Una vez que ejecutabas `freeze()`, no solo ese objeto se bloqueaba, sino todo su subgrafo de propiedades y referencias dependientes. Si por accidente intentabas modificar cualquier propiedad de un objeto previamente congelado, la aplicación lanzaba una excepción nativa `InvalidMutabilityException` y se cerraba inesperadamente ("crasheaba").

Este sistema obligaba a los desarrolladores a pelear constantemente contra la arquitectura, recurriendo a trucos complejos para compartir el estado. Hacía que patrones de uso común, como el manejo de repositorios, los flujos reactivos mutables o la inyección de dependencias, fueran difíciles y propensos a errores en la plataforma de Apple.

Afortunadamente, esto desapareció con la llegada del Nuevo Modelo de Memoria (introducido y consolidado a partir de Kotlin 1.7.20). JetBrains reescribió la forma en que el Recolector de Basura (Garbage Collector) y la memoria nativa interactúan, logrando que Kotlin en iOS se comporte de una forma muy similar a la Máquina Virtual de Java (JVM).

Hoy en día, puedes compartir objetos mutables libremente entre múltiples hilos y emplear Corrutinas sin el temor a excepciones de inmutabilidad. Por eso, cualquier tutorial o base de código que incluya referencias a `freeze()` se considera obsoleto y el comando debe ser eliminado.