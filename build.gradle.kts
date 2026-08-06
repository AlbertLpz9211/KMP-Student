plugins {
    kotlin("jvm")
    application
}

group = "equipo3"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("patrones.CatalogoDemoKt")
}

val demos = mapOf(
    "runChain" to "patrones.chainofresponsibility.DemoKt",
    "runCommand" to "patrones.command.DemoKt",
    "runMediator" to "patrones.mediator.DemoKt",
    "runMemento" to "patrones.memento.DemoKt",
    "runObserver" to "patrones.observer.DemoKt",
    "runState" to "patrones.state.DemoKt",
    "runStrategy" to "patrones.strategy.DemoKt",
    "runVisitor" to "patrones.visitor.DemoKt"
)

demos.forEach { (taskName, className) ->
    tasks.register<JavaExec>(taskName) {
        group = "application"
        description = "Ejecuta la demostración de $taskName"
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set(className)
    }
}