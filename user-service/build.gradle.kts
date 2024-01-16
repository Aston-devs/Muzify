val lombokVersion = "1.18.30"
val jwt = "0.11.5"
val commonsLang = "3.12.0";

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:$jwt")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwt")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwt")
    implementation("org.apache.commons:commons-lang3:$commonsLang")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.flywaydb:flyway-core")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
}
