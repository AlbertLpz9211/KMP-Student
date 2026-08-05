package patrones.builder

// 1. CLASE PRODUCTO (ReporteConfiguracion)
// Es el objeto complejo que queremos construir.
// Su constructor se define como 'internal constructor' para restringir su instanciación directa
// y forzar que la creación se haga a través del Builder.
class ReporteConfiguracion internal constructor(
    val titulo: String,
    val tieneGraficos: Boolean,
    val tieneTablaDatos: Boolean
)

// 2. CLASE BUILDER (ReporteBuilder)
// Se encarga de acumular la configuración paso a paso y finalmente instanciar el producto.
class ReporteBuilder {
    // Definimos variables privadas con valores por defecto.
    // Si el usuario no las configura, el reporte mantendrá estos estados base.
    private var titulo: String = "Sin Título"
    private var tieneGraficos: Boolean = false
    private var tieneTablaDatos: Boolean = false

    // Método para asignar el título.
    // Usamos la función de alcance 'apply' de Kotlin, la cual ejecuta el bloque sobre la instancia
    // y devuelves 'this' automáticamente (Fluent Interface) para permitir el encadenamiento.
    fun setTitulo(titulo: String): ReporteBuilder = apply {
        this.titulo = titulo
    }

    // Método fluido para definir si se incluyen gráficos
    fun incluirGraficos(incluir: Boolean): ReporteBuilder = apply {
        this.tieneGraficos = incluir
    }

    // Método fluido para definir si se incluyen tablas de datos
    fun incluirTablaDatos(incluir: Boolean): ReporteBuilder = apply {
        this.tieneTablaDatos = incluir
    }

    // Método finalizador (.build())
    // Toma todas las variables recolectadas y las pasa al constructor del objeto final.
    fun build(): ReporteConfiguracion {
        return ReporteConfiguracion(titulo, tieneGraficos, tieneTablaDatos)
    }
}