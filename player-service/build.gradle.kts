dependencies {
    implementation("software.amazon.awssdk:s3:2.17.84")
    implementation ("org.springframework.kafka:spring-kafka")
    implementation(platform("software.amazon.awssdk:bom:2.17.84"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    testImplementation ("org.springframework.kafka:spring-kafka-test")
}
