# Sesión 8 — Interoperabilidad con iOS
## Qué se hizo

<!-- primero el cambio visible en la app iOS: ahora tiene dos pantallas. -->

1. La app iOS ahora tiene dos caras en un `TabView`:
    - `Compose`: la pantalla compartida existente hecha con Compose Multiplatform.
    - `SwiftUI`: una pantalla nativa de iOS hecha con SwiftUI.



2. La pantalla SwiftUI usa el `MovieListViewModel` de Kotlin mediante `IosMovieListBridge`.
    - SwiftUI observa el estado de Kotlin (`MovieListState`).
    - Muestra populares, búsqueda, carga, error, estado vacío y paginación.
    - El buscador de SwiftUI llama a `onQueryChange` del ViewModel compartido.
    - Pull-to-refresh llama a `refrescar` del ViewModel compartido.

## Archivos modificados

<!-- Esta sirve para ubicar rapido que archivos toque para la entrega. -->

- `iosApp/iosApp/ContentView.swift`: agrega el `TabView` y la pantalla SwiftUI nativa.
- `shared/src/iosMain/kotlin/com/jetbrains/kmpapp/presentation/IosMovieListBridge.kt`: puente pequeño para recolectar el `StateFlow` desde iOS.
- `docs/sesion8.md`: resumen de la realice para la entrega de la tarea.

## Qué hace SKIE y por qué facilita la vida


SKIE mejora cómo Swift ve APIs de Kotlin Multiplatform.
Convierte tipos comunes de Kotlin, como `Flow`, en formas más naturales para Swift.
Con SKIE, un `Flow` puede recorrerse desde Swift como `AsyncSequence`.
Eso reduce puentes manuales y hace que el código SwiftUI sea más limpio.
En este proyecto no estaba configurado, por eso se usó un puente iOS mínimo para consumir el ViewModel.


