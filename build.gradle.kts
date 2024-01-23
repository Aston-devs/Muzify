plugins {
    id("java")
    id("checkstyle")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}

subprojects {
    group = "ru.musify"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "checkstyle")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")


    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    checkstyle {
        toolVersion = "10.3.2"
        isIgnoreFailures = false
        maxWarnings = 0
        maxErrors = 0
        configFile = file("$rootDir/config/checkstyle/checkstyle.xml")
    }

    extra["springCloudVersion"] = "2023.0.0"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.test {
        useJUnitPlatform()
    }

    configurations {
        compileOnly {
            extendsFrom(annotationProcessor.get())
        }
    }
}
