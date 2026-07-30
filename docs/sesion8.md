# Sesión 8 — Interoperabilidad con iOS

## SKIE

SKIE facilita el uso del código Kotlin desde Swift.  
Permite usar las funciones `suspend` como funciones `async` en Swift.  
También permite recorrer los `Flow` y `StateFlow` como secuencias asíncronas.  
Gracias a esto, SwiftUI puede recibir los cambios del ViewModel de Kotlin.  
Así se reutiliza la lógica compartida sin volver a crearla en iOS.

