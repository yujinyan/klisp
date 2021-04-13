import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.32"
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

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
  testImplementation("io.kotest:kotest-assertions-core:4.4.3")
  implementation(kotlin("stdlib"))
}
