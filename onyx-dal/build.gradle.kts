plugins {
    java
}

group = "com.onyx"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation(project(":onyx-commons"))

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.1.0") {
        // Checkmarx on 06/04/23
        // CVE-2022-41854 6.5 Out-of-bounds Write vulnerability with medium severity found
        // CVE-2022-1471 9.8 Deserialization of Untrusted Data vulnerability with high severity found
        exclude("org.yaml:snakeyaml:1.33")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}