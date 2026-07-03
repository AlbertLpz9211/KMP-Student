# 🎬 CineKMP — Repositorio Semilla (Bootcamp Kotlin Multiplatform)

Proyecto base **funcional** para el Bootcamp KMP (julio 2026). Arranca desde la plantilla
oficial de JetBrains (Kotlin Multiplatform + Compose Multiplatform) y es el punto de
partida que irás transformando en **CineKMP** a lo largo de las 9 sesiones.

- **Kotlin** 2.3.21 · **Gradle** 9.3.1 · **Compose Multiplatform** 1.11.0 · **AGP** 9.0.1
- Ya incluye: **Ktor** (red), **kotlinx.serialization**, **Koin** (DI), **ViewModels**
  compartidos, **Navigation Compose**, **Coil** (imágenes) y UI compartida en Compose MP.
- Targets: **Android** + **iOS** (UI 100% compartida).

> ℹ️ **Importante:** el seed corre hoy como una pequeña app de ejemplo (catálogo del
> *Metropolitan Museum*, con una **API pública que NO requiere key**). Eso demuestra que
> todo compila y corre en Android e iOS. A partir de la **Sesión 3** reemplazarás su capa
> de datos por la de **TMDB (películas)** para convertirla en CineKMP. Empezar de una app
> que ya funciona y refactorizarla es exactamente el flujo real de trabajo.

---

## 📁 Estructura del proyecto

```
CineKMP/
├── gradlew / gradlew.bat          # Gradle wrapper (NO necesitas instalar Gradle)
├── settings.gradle.kts            # rootProject.name = "CineKMP"
├── gradle/libs.versions.toml      # Version catalog (todas las dependencias/versiones)
├── shared/                        # 🧠 MÓDULO COMPARTIDO (el corazón del curso)
│   └── src/
│       ├── commonMain/kotlin/com/jetbrains/kmpapp/
│       │   ├── App.kt             # UI raíz + navegación (Compose MP)
│       │   ├── data/              # Ktor API, modelos, repositorio  ← Sesión 3 y 4
│       │   ├── di/Koin.kt         # Inyección de dependencias        ← Sesión 5
│       │   └── screens/           # list/ y detail/ (Screen + ViewModel) ← Sesión 5,6,7
│       ├── commonMain/composeResources/  # strings/imágenes compartidas
│       └── iosMain/kotlin/.../MainViewController.kt  # punto de entrada iOS
├── androidApp/                    # App Android (host de la UI compartida)
│   └── src/main/kotlin/.../MainActivity.kt
└── iosApp/                        # App iOS (proyecto Xcode)
    ├── iosApp.xcodeproj
    └── iosApp/ (ContentView.swift, iOSApp.swift)
```

> El nombre de paquete se mantiene como `com.jetbrains.kmpapp` a propósito (renombrarlo
> tocaría el proyecto Xcode y podría romper la compilación). Puedes renombrarlo más
> adelante como ejercicio avanzado.

---

## ✅ Requisitos previos

| Herramienta | Estado en tu equipo | Nota |
|---|---|---|
| **JDK** | ✅ Java 26 detectado (Gradle 9.3.1 corre bien con él) | Recomendado LTS **21** si algo falla |
| **Android Studio** | Necesario para Android/emulador | Instala el plugin *Kotlin Multiplatform* |
| **Xcode completo** | ⚠️ **Solo tienes Command Line Tools** | **Instala Xcode** desde la App Store para compilar iOS |
| **Gradle** | No hace falta instalarlo | Se usa el *wrapper* (`./gradlew`) |

> ⚠️ **Para iOS:** hoy solo tienes las *Command Line Tools*. Antes de correr en iPhone/simulador
> debes instalar **Xcode** (App Store) y ejecutar una vez:
> `sudo xcode-select -s /Applications/Xcode.app/Contents/Developer`

---

## ▶️ Cómo correrlo

### Opción A — Android por línea de comandos (lo más rápido para probar)

```bash
cd ~/repositorios/CineKMP

# 1) Verifica que el wrapper funciona (ya validado ✅)
./gradlew --version

# 2) Compila el módulo compartido y la app Android (la 1ª vez descarga dependencias)
./gradlew :shared:assemble
./gradlew :androidApp:assembleDebug

# 3) Con un emulador o dispositivo Android conectado, instala y lanza:
./gradlew :androidApp:installDebug
```

El APK queda en `androidApp/build/outputs/apk/debug/`.

### Opción B — Android Studio (recomendado para desarrollar)

1. Abre **Android Studio** → *Open* → selecciona la carpeta `~/repositorios/CineKMP`.
2. Espera el *Gradle sync* (la primera vez tarda: descarga Kotlin/Native, dependencias, etc.).
3. Arriba, en el selector de configuraciones, elige **androidApp** + un emulador → botón ▶️ *Run*.

### Opción C — iOS (requiere Xcode instalado)

1. Instala **Xcode** y configúralo (ver requisitos arriba).
2. Abre `iosApp/iosApp.xcodeproj` en Xcode.
3. Selecciona un simulador (ej. *iPhone 16*) y pulsa ▶️ *Run*.
   Xcode ejecutará automáticamente la tarea de Gradle que compila el framework Kotlin
   (`embedAndSignAppleFrameworkForXcode`) y lo enlaza a la app.

> Alternativa: con el plugin *Kotlin Multiplatform* en Android Studio puedes lanzar el
> target **iosApp** directamente desde el IDE (requiere Xcode instalado).

### Comandos útiles

```bash
./gradlew tasks                       # lista todas las tareas disponibles
./gradlew :shared:allTests            # corre los tests del módulo compartido (todas las plataformas)
./gradlew clean                       # limpia builds
./gradlew :shared:iosSimulatorArm64Test   # tests solo en simulador iOS
```

---

## 🧭 Cómo mapea con el Bootcamp

| Sesión | Qué tocarás en este repo |
|---|---|
| **1** (Vie 3 jul) | Correr el seed en Android/iOS; explorar `shared/` y `expect`/`actual`. |
| **2** (Jue 9 jul) | Añadir utilidades multiplataforma y practicar Corrutinas/Flow. |
| **3** (Vie 10 jul) | Reemplazar `data/MuseumApi.kt` por `TmdbApi` (películas) + DTOs/mappers. |
| **4** (Jue 16 jul) | Añadir **SQLDelight** (persistencia offline-first) al módulo `data/`. |
| **5** (Vie 17 jul) | Reorganizar en `domain/data/presentation/di`; casos de uso; Koin. |
| **6** (Jue 23 jul) | Evolucionar `screens/list` y `screens/detail` (Compose MP). |
| **7** (Vie 24 jul) | Navegación avanzada, búsqueda, favoritos, Coil, interop de UI. |
| **8** (Jue 30 jul) | Consumir el `shared` desde **SwiftUI nativo** + SKIE en `iosApp/`. |
| **9** (Vie 31 jul) | Tests, **CI (GitHub Actions)** y entrega del proyecto final. |

Consulta la teoría y las tareas en:
- `~/Desktop/Kotlin_Multiplatform_KMP/Manual_Bootcamp_KMP.md`
- `~/Desktop/Kotlin_Multiplatform_KMP/KMP_Base_de_Conocimiento.md`

---

## 🐛 Solución de problemas

- **El primer build tarda mucho:** normal. Descarga Gradle 9.3.1, el toolchain de
  Kotlin/Native (cientos de MB) y las dependencias. Ten paciencia y buena conexión.
- **Error de JDK / toolchain:** si algo falla con Java 26, instala **JDK 21 (LTS)** y añade
  en `gradle.properties`:
  `org.gradle.java.home=/ruta/a/tu/jdk-21`
- **iOS no compila:** casi siempre es porque falta **Xcode completo** (no bastan las Command
  Line Tools). Instálalo y ejecuta `sudo xcode-select -s /Applications/Xcode.app/Contents/Developer`.
- **`kdoctor`** te diagnostica el entorno KMP: `brew install kdoctor && kdoctor`.

---

*Seed generado para el Bootcamp KMP · julio 2026. Basado en la plantilla oficial
`Kotlin/kmp-app-template` de JetBrains (licencia Apache 2.0, incluida en `LICENSE`).*
