import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  application
  kotlin("jvm") version "1.4.32"
  id("com.palantir.graal") version "0.7.2"
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "me.yujinyan"
version = "1.0-SNAPSHOT"

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    useIR = true
    freeCompilerArgs = listOf("-Xinline-classes")
  }
}

tasks.withType<ShadowJar>().configureEach {
  archiveFileName.set("klisp.jar")
}

application {
  // https://github.com/johnrengelman/shadow/pull/612
  // mainClass.set("MainKt")
  mainClassName = "MainKt"
}

graal {
  javaVersion("11")
  mainClass("MainKt")
  outputName("klisp")
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
  testImplementation("io.kotest:kotest-assertions-core:4.4.3")
  implementation("com.github.ajalt.clikt:clikt:3.1.0")
  implementation(kotlin("stdlib"))
}
