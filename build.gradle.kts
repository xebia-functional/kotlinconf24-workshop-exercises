import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "2.0.0-RC3"
  id("com.google.devtools.ksp") version "2.0.0-RC3-1.0.20"
  id("org.jetbrains.kotlin.plugin.power-assert") version "2.0.0-RC3"
}


group = "com.xebia"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val arrowVersion = "2.0.0-alpha.1"

dependencies {
  implementation("io.arrow-kt:arrow-core:$arrowVersion")
  implementation("io.arrow-kt:arrow-fx-stm:$arrowVersion")
  implementation("io.arrow-kt:arrow-fx-coroutines:$arrowVersion")
  implementation("io.arrow-kt:arrow-optics:$arrowVersion")
  ksp("io.arrow-kt:arrow-optics-ksp-plugin:$arrowVersion")
  implementation("io.github.nomisrev:kotlinx-serialization-jsonpath:$arrowVersion")
  testImplementation(kotlin("test"))
  testImplementation("net.jqwik:jqwik:1.6.5")
}

tasks {
  withType<KotlinCompile> {
    compilerOptions.freeCompilerArgs.add("-Xcontext-receivers")
  }

  test {
    useJUnitPlatform()
  }
}

@Suppress("OPT_IN_USAGE")
powerAssert {
  functions = listOf("kotlin.test.assertEquals")
  functions = listOf("kotlin.test.assertTrue")
}