import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
  id("org.jetbrains.kotlin.multiplatform") version "1.9.0"
  id("org.jetbrains.dokka") version "1.8.20"
}

repositories {
  mavenCentral()
}

kotlin {
  explicitApi()

  jvm()
  linuxX64()
  macosX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api("io.ktor:ktor-server-core:2.3.2")
        implementation("io.ktor:ktor-server-cio:2.3.2")
        implementation("io.ktor:ktor-server-auto-head-response:2.3.2")
        implementation("io.ktor:ktor-server-content-negotiation:2.3.2")
        implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
        implementation("io.ktor:ktor-server-double-receive:2.3.2")
        api("org.jetbrains.kotlinx:kotlinx-html:0.9.1")
        implementation("io.ktor:ktor-server-html-builder:2.3.2")
        implementation("ch.qos.logback:logback-classic:1.4.8")
        implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")
        implementation("io.arrow-kt:suspendapp:0.4.0")
        implementation("io.arrow-kt:suspendapp-ktor:0.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
      }
    }
  }
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
  }
}

tasks.dokkaHtml.configure {
  outputDirectory.set(rootDir.resolve("docs"))
  moduleName.set("SuperSimpleKtor")
  dokkaSourceSets {
    named("commonMain") {
      includes.from("README.md")
    }
  }
}

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets.configureEach {
    externalDocumentationLink {
      url.set(URL("https://api.ktor.io/"))
    }
  }
}

val javadocJar by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
  from(tasks.dokkaHtml)
}
