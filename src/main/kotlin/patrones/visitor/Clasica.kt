package patrones.visitor

interface ProductoVisitable {
    fun aceptar(visitor: VisitorProducto): Double
}

class LibroClasico(
    val precio: Double
) : ProductoVisitable {

    // Aceptar permite elegir la operacion correspondiente del visitante.
    override fun aceptar(visitor: VisitorProducto): Double =
        visitor.visitar(this)
}

class ElectronicoClasico(
    val precio: Double
) : ProductoVisitable {

    override fun aceptar(visitor: VisitorProducto): Double =
        visitor.visitar(this)
}

interface VisitorProducto {
    fun visitar(libro: LibroClasico): Double
    fun visitar(electronico: ElectronicoClasico): Double
}

class CalculadorImpuestoVisitor : VisitorProducto {
    override fun visitar(libro: LibroClasico): Double = 0.0

    override fun visitar(electronico: ElectronicoClasico): Double =
        electronico.precio * 0.16
}
