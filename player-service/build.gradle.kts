val lombokVersion = "1.18.20"
val mapstructVersion = "1.5.5.Final"
val lombokMapstructBindingVersion = "0.2.0"

dependencies {
    implementation("software.amazon.awssdk:s3:2.17.84")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation(platform("software.amazon.awssdk:bom:2.17.84"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}
