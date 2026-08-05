package patrones.composite

interface ElementoMilitar {
    fun desplegarEstrategia(prefijo: String = "")
    fun calcularPoderDeFuego(): Int
}

class CompaniaMilitar(private val codigoUnidad: String) : ElementoMilitar {
    private val subordinados = mutableListOf<ElementoMilitar>()

    fun incorporar(elemento: ElementoMilitar) {
        subordinados.add(elemento)
    }

    fun retirar(elemento: ElementoMilitar) {
        subordinados.remove(elemento)
    }

    override fun desplegarEstrategia(prefijo: String) {
        println("$prefijo[Compañía / División] Unidad $codigoUnidad")
        for (elemento in subordinados) {
            elemento.desplegarEstrategia("$prefijo    ")
        }
    }

    override fun calcularPoderDeFuego(): Int {
        var poderTotal = 0
        for (elemento in subordinados) {
            poderTotal += elemento.calcularPoderDeFuego()
        }
        println("[Unidad $codigoUnidad] Poder acumulado calculado: $poderTotal unidades de ataque.")
        return poderTotal
    }
}

class SoldadoInfanteria(private val identificador: String, private val poderBase: Int) : ElementoMilitar {
    override fun desplegarEstrategia(prefijo: String) {
        println("$prefijo[Soldado Raso] $identificador (Poder: $poderBase)")
    }

    override fun calcularPoderDeFuego(): Int {
        return poderBase
    }
}