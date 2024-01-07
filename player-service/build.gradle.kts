dependencies {
    implementation("software.amazon.awssdk:s3:2.17.84")
    implementation(platform("software.amazon.awssdk:bom:2.17.84"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    testImplementation("io.projectreactor:reactor-test")
}
