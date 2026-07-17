# Respuestas al Examen - RickMortyKMP

### (a) ¿Por qué usar Flow (y no una función suspend que devuelve una lista) para un flujo de datos que cambia en el tiempo?
`Flow` permite representar una corriente reactiva de datos que puede emitir múltiples valores a lo largo del tiempo, lo que es ideal para observar cambios automáticos (por ejemplo, desde una base de datos local). A diferencia de una función `suspend`, que solo devuelve un resultado único y finaliza, un `Flow` mantiene la conexión con la fuente de datos, permitiendo que la UI se actualice automáticamente cada vez que los datos subyacentes cambien sin necesidad de volver a solicitar la información manualmente.

### (b) ¿Qué hace ignoreUnknownKeys = true y por qué es útil con una API real como Rick & Morty?
La opción `ignoreUnknownKeys = true` en Kotlinx Serialization permite que el deserializador ignore cualquier campo presente en el JSON que no esté explícitamente definido en nuestra clase DTO. Esto es crucial en APIs reales como Rick & Morty porque estas pueden evolucionar y añadir nuevos campos en sus respuestas; sin esta opción, nuestra aplicación lanzaría una excepción y dejaría de funcionar al encontrar una clave desconocida, rompiendo la compatibilidad hacia adelante.

### (c) ¿Qué significa que DriverFactory sea expect/actual en vez de una sola clase en commonMain?
Significa que estamos utilizando el mecanismo de abstracción de Kotlin Multiplatform para manejar código dependiente de la plataforma. La base de datos SQLDelight requiere un "driver" específico para interactuar con el sistema operativo (SQLite en Android vs Native en iOS); al usar `expect`, definimos la interfaz común en `commonMain`, mientras que con `actual` proporcionamos la implementación técnica específica en cada módulo de plataforma, permitiendo que el código compartido use la base de datos sin conocer los detalles de bajo nivel de cada sistema.
