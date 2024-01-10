dependencyManagement {
    imports {
        mavenBom ("org.keycloak.bom:keycloak-adapter-bom:12.0.3")
    }
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    compileOnly ("org.projectlombok:lombok:1.18.30")
    annotationProcessor ("org.projectlombok:lombok:1.18.30")

    runtimeOnly ("org.springframework.boot:spring-boot-devtools")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

