# Sesión 2 - Notas de Tarea

## ¿Qué era `freeze()` y por qué desapareció?

En el modelo de memoria viejo de Kotlin/Native (el que usaba iOS), existía una regla muy estricta: si querías pasar un objeto de un hilo a otro, tenías que "congelarlo" con la función `freeze()`. Al congelarlo, el objeto se volvía inmutable (ya no podías cambiar sus datos). 

Esto desapareció con el **Nuevo Modelo de Memoria** porque era un dolor de cabeza para los desarrolladores y causaba muchos cierres inesperados (crashes). Ahora, Kotlin Multiplatform permite compartir objetos entre hilos de forma mucho más sencilla y automática, casi igual a como lo hace Java o Swift normalmente, sin necesidad de andar congelando cosas.
