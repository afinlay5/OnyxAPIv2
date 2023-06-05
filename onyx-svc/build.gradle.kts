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

    annotationProcessor("org.projectlombok:lombok:1.18.28")
    compileOnly("org.projectlombok:lombok:1.18.28")

    implementation(project(":onyx-commons"))
    implementation(project(":onyx-dal"))

    implementation("com.google.guava:guava:32.0.0-jre") //TODO get specific modules only (ImmutableList)
    implementation("org.apache.commons:commons-lang3:3.12.0") // //TODO get specific modules only (NotImplementedException)
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.springframework:spring-context:6.0.9")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}