plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation ("com.github.stefanbirkner:system-lambda:1.2.0")
}



kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}