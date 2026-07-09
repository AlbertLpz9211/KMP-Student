# commonMain: 
Es el código base que escribes una sola vez y que funciona en todos lados (como el motor de un coche que sirve para cualquier chasis).

# ¿Por qué no java.util.UUID?: 
Porque ese código pertenece a Java. Android lo entiende, pero iOS no tiene idea de qué es Java. Si usas código de Java en la parte común, iOS te dará error porque no sabe leerlo.

# expect / actual:

expect: En el código común, dices: "Necesito esta función, pero no sé cómo hacerla todavía".

actual: En cada plataforma, dices: "Aquí está la solución real usando mis herramientas nativas".ills.kt` para verificar los ejercicios básicos.