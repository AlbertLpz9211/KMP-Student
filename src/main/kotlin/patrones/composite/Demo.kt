package patrones.composite

fun main() {
    println("--- BIENVENIDO AL SISTEMA DE GESTIÓN MILITAR (COMPOSITE) ---")

    val ejercitoGlobal = CompaniaMilitar("Batallón Alfa-1")
    val divisionNorte = CompaniaMilitar("División Norte")
    val divisionSur = CompaniaMilitar("División Sur")

    val soldado1 = SoldadoInfanteria("Rico-01", 10)
    val soldado2 = SoldadoInfanteria("Kowalski-02", 15)
    val soldado3 = SoldadoInfanteria("Private-03", 5)

    divisionNorte.incorporar(soldado1)
    divisionNorte.incorporar(soldado2)
    divisionSur.incorporar(soldado3)

    ejercitoGlobal.incorporar(divisionNorte)
    ejercitoGlobal.incorporar(divisionSur)

    println("\n--- DESPLIEGUE JERÁRQUICO DE TROPAS ---")
    ejercitoGlobal.desplegarEstrategia()

    println("\n--- CÁLCULO DE PODER TOTAL DE COMBATE ---")
    val poderTotalEjercito = ejercitoGlobal.calcularPoderDeFuego()
    println("\nPoder bélico total del ejército: $poderTotalEjercito unidades.")
    println("--- DEMOSTRACIÓN COMPOSITE FINALIZADA ---")
}