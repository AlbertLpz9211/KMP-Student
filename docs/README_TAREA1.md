 Mis notas de la Tarea 1 - CineKMP
 ¿Qué es CineKMP?
Es básicamente una app que estamos haciendo para ver pelis, pero lo especial es que no la escribimos dos veces (una para Android y otra para iPhone). Escribimos la lógica una sola vez en "Kotlin Multiplatform" y luego cada celular la usa. Así nos ahorramos un montón de trabajo y evitamos errores.

 Cositas técnicas (pero explicadas fácil)

¿Por qué no pude usar `java.util.UUID` así nomás?
Resulta que `java.util.UUID` es algo que solo vive en el mundo de Java y Android. Si trato de usarlo en la parte "compartida" de la app, el iPhone no va a saber qué hacer con eso porque él no habla Java. Es como tratar de pagar con pesos en una tienda que solo acepta euros; simplemente no funciona.

Entonces, ¿qué onda con `expect` y `actual`?
Es como dejar un post-it en la cocina. 
- Con **`expect`** pongo una nota que dice: "Oigan, necesito que alguien me consiga un ID único, no me importa cómo lo hagan".
- Luego, en la carpeta de **Android**, pongo el **`actual`** que dice: "Ah, yo uso mi herramienta de Java para eso".
- Y en la carpeta de **iOS**, pongo otro **`actual`** que dice: "Yo uso mi herramienta de Apple para lo mismo".

Al final, la app principal solo lee la nota del post-it y todo funciona perfecto en ambos lados.
