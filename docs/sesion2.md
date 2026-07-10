# Tarea 2 - Sesión 2

## Evidencia de Tests en Verde
![Tests en Verde](captura_tests.png)

## Pregunta Teórica: El uso de freeze() en iOS
En las primeras versiones de Kotlin Native, el manejo de memoria en iOS era muy estricto y no permitía que varios hilos de ejecución modificaran o accedieran
al mismo objeto al mismo tiempo para evitar fallos de concurrencia. Por esta razón, se utilizaba la función freeze(), la cual congelaba un objeto volviéndolo 
completamente inmutable y permitía compartirlo de forma segura entre diferentes hilos. Con la llegada del New Memory Model en Kotlin 1.7.20, estas limitaciones 
se eliminaron y ahora el sistema gestiona la concurrencia y la memoria de manera automática, igual que en Android. Al volverse un proceso nativo y directo, 
el uso de freeze() quedó totalmente obsoleto y ya no es necesario implementarlo.