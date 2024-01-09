plugins {
    id("org.openapi.generator") version "5.2.1"
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("org.openapitools:openapi-generator:5.2.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.openapitools:jackson-databind-nullable:0.1.0")

    compileOnly("org.jetbrains:annotations:16.0.2")
    annotationProcessor("org.jetbrains:annotations:16.0.2")
}

openApiGenerate {
    val openapiGroup = "$group.api.v1"
    generatorName.set("java")
    library.set("rest-assured")
    packageName.set(openapiGroup)
    generateModelTests.set(false)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/specification/specification-musify-model-v1.yml")

    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    configOptions.set(
            mapOf(
                    "dateLibrary" to "java8",
                    "openApiNullable" to "false",
                    "dynamicOperations" to "true",
                    "serializableModel" to "true",
                    "sourceFolder" to "src/main/java",
                    "serializationLibrary" to "jackson",
                    "enumPropertyNaming" to "UPPERCASE",
                    "prependFormOrBodyParameters" to "true",
                    "additionalModelTypeAnnotations" to "@lombok.AllArgsConstructor;@lombok.NoArgsConstructor;@lombok.Builder"
            )
    )
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}

sourceSets {
    main {
        java {
            srcDirs(file("build/generate-resources/main/src/main/java/"))
        }
    }
}