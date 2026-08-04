package patrones.flyweight

/**
 * ESTADO INTRÍNSECO (Flyweight):
 * Representa los datos pesados y compartidos que no cambian entre unidades del mismo tipo.
 * En un juego real, aquí estarían las texturas 3D y sonidos pesados.
 */
class TipoUnidad(
    val nombre: String,
    val statsBase: String,
    val iconoTextura: String // Simulamos una textura pesada
) {
    fun renderizar(x: Int, y: Int, salud: Int) {
        println("Dibujando [$nombre] en ($x, $y) con salud: $salud% - Stats Base: [$statsBase] - Usando textura: $iconoTextura")
    }
}

/**
 * FÁBRICA DE FLYWEIGHTS:
 * Se encarga de gestionar y reutilizar los objetos compartidos. 
 * Si el tipo ya existe, lo devuelve; si no, lo crea.
 */
object FabricaUnidades {
    private val tipos = mutableMapOf<String, TipoUnidad>()

    fun getTipo(nombre: String, stats: String, textura: String): TipoUnidad {
        return tipos.getOrPut(nombre) {
            println(">> [SISTEMA] Creando nuevo tipo de unidad pesada: $nombre")
            TipoUnidad(nombre, stats, textura)
        }
    }

    fun totalTiposCreados(): Int = tipos.size
}

/**
 * CONTEXTO (Estado Extrínseco):
 * Representa los datos únicos de cada soldado. 
 * Solo guarda una referencia al tipo compartido (Flyweight) y sus propios datos ligeros.
 */
class Soldado(
    val id: Int,
    private val tipo: TipoUnidad, // Referencia al Flyweight
    var x: Int,
    var y: Int,
    var salud: Int = 100
) {
    fun dibujar() {
        print("Soldado Individual #$id -> ")
        tipo.renderizar(x, y, salud)
    }
}
