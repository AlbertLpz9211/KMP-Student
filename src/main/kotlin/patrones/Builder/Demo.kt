package patrones.builder

// Función principal donde probamos el patrón Builder
fun main() {
    println("--- DEMOSTRACIÓN PATRÓN BUILDER ---")

    // --- 1. Construcción de Reporte Completo ---
    // Usamos el Builder usando encadenamiento de métodos (Fluent Interface).
    // Esto nos permite configurar solo las propiedades que necesitamos paso a paso.
    val reporteCompleto = ReporteBuilder()
        .setTitulo("Reporte Financiero Q3") // Configuramos el título
        .incluirGraficos(true)               // Habilitamos los gráficos
        .incluirTablaDatos(true)             // Habilitamos las tablas
        .build()                             // Construimos y obtenemos el objeto final (Reporte)

    // Imprimimos el estado del objeto complejo recién creado
    println("Reporte Creado: Título='${reporteCompleto.titulo}', Gráficos=${reporteCompleto.tieneGraficos}, Tablas=${reporteCompleto.tieneTablaDatos}")

    // --- 2. Construcción de Reporte Básico ---
    // Creamos otro reporte pero solo configurando el título.
    // El Builder asignará valores por defecto a los campos opcionales que omitimos.
    val reporteBasico = ReporteBuilder()
        .setTitulo("Resumen Ejecutivo")
        .build()

    println("Reporte Creado: Título='${reporteBasico.titulo}', Gráficos=${reporteBasico.tieneGraficos}, Tablas=${reporteBasico.tieneTablaDatos}")

    println("\n--- DEMOSTRACIÓN BUILDER FINALIZADA ---")
}