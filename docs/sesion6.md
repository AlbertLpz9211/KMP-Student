# Sesión 6 — Compose Multiplatform I (UI con estados)

La misma UI en Compose se dibuja en Android **e** iOS.

## Qué se hizo

1. **Tema propio** (`theme/CineTheme.kt`): paleta "de cine" (rojo + dorado) con variante clara/oscura
   y tipografía con títulos en negrita. `App.kt` ahora envuelve todo en `CineTheme`.

2. **Componentes reutilizables** (`screens/components/MovieComponents.kt`):
   - `MoviePoster`: imagen con Coil (`AsyncImage`) + marcador de posición 🎬.
   - `LoadingState`, `ErrorState` (con botón **Reintentar**), `EmptyState`.

3. **Pantalla de lista** (`screens/list/ListScreen.kt`): cuadrícula adaptable (`LazyVerticalGrid`)
   de pósters, con los **4 estados** (cargando · error+reintentar · vacío · datos) y **pull-to-refresh**
   (`PullToRefreshBox`).

4. **Pantalla de detalle** (`screens/detail/DetailScreen.kt`): imagen de cabecera, título, rating,
   año, tagline/géneros/duración (de la red) y **botón de FAVORITO** real.

## Idea clave (para explicar en clase)

- La UI **observa** el `StateFlow` del ViewModel con `collectAsStateWithLifecycle` y se **redibuja sola**.
- El corazón de favorito funciona porque `state.pelicula` viene de un **Flow de la DB**: al pulsar,
  se guarda en SQLite → el Flow emite → Compose recompone. No hay que "refrescar" a mano.

## Correr

```bash
# Android: selecciona androidApp + emulador y ▶️ Run
# iOS: selecciona iosApp + simulador y ▶️ Run  (misma pantalla, otro sistema)
```

> Capturas (Android e iOS lado a lado): pendientes de generar al ejecutar en clase.
