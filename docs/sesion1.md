# Sesión 1

## ¿Qué es commonMain?

`commonMain` es la parte del proyecto donde va el código compartido de Kotlin. Ese código puede funcionar tanto en Android como en iOS, por eso no debe usar directamente cosas exclusivas de una sola plataforma.

## ¿Por qué commonMain no puede usar java.util.UUID?

Porque `java.util.UUID` pertenece a Java/JVM. Android puede usarlo, pero iOS no. Si se usa directamente en `commonMain`, el código compartido dejaría de servir igual para iOS. Para resolver eso se usa `expect/actual`.

## ¿Qué hace expect/actual?

`expect` declara una función en el código común y `actual` la implementa en cada plataforma. En esta tarea se creó `infoDispositivo()`: en Android usa `Build.MODEL` y en iOS usa `UIDevice.currentDevice.model`.

## Evidencia

La captura de la app corriendo en Android está en:

`docs/android_corriendo.png`