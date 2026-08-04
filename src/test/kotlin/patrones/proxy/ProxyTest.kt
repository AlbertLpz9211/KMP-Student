package patrones.proxy

import kotlin.test.Test
import kotlin.test.assertFailsWith

class ProxyTest {

    @Test
    fun testSitioSeguroPermiteConexion() {
        val proxy = FirewallProxy()
        // No debería lanzar ninguna excepción
        proxy.conectarA("google.com")
        proxy.conectarA("kotlinlang.org")
    }

    @Test
    fun testSitioEnListaNegraLanzaExcepcion() {
        val proxy = FirewallProxy()
        
        val excepcion = assertFailsWith<AccesoDenegadoException> {
            proxy.conectarA("facebook.com")
        }
        println("Bloqueo confirmado: ${excepcion.message}")
    }

    @Test
    fun testBusquedaInsensibleAMayusculas() {
        val proxy = FirewallProxy()
        
        val excepcion = assertFailsWith<AccesoDenegadoException> {
            proxy.conectarA("TIKTOK.COM")
        }
        println("Bloqueo de mayúsculas confirmado: ${excepcion.message}")
    }

    @Test
    fun testBusquedaParcialBloquea() {
        val proxy = FirewallProxy()
        
        val excepcion = assertFailsWith<AccesoDenegadoException> {
            proxy.conectarA("https://www.youtube.com/watch?v=123")
        }
        println("Bloqueo parcial confirmado: ${excepcion.message}")
    }
}
