package patrones.visitor

sealed interface Producto {
    val precio: Double

    data class Libro(
        override val precio: Double
    ) : Producto

    data class Electronico(
        override val precio: Double
    ) : Producto
}

fun calcularImpuesto(producto: Producto): Double =
    // El when es exhaustivo porque Producto es una jerarquia sellada.
    when (producto) {
        is Producto.Libro -> 0.0
        is Producto.Electronico -> producto.precio * 0.16
    }
