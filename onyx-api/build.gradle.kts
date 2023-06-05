plugins {
    java
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.20"
    id("com.diffplug.spotless") version "6.19.0"
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

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    implementation(project(":onyx-svc"))
    implementation(project(":onyx-commons"))


    implementation("com.google.guava:guava:32.0.0-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//The api configuration should be used to declare dependencies which are exported by the library API,
// whereas the implementation configuration should be used to declare dependencies which are internal to the component.

//./gradlew :onyx-commons:clean :onyx-commons:build
//./gradlew :onyx-dal:clean :onyx-dal:build
//./gradlew :onyx-svc:clean :onyx-svc:build
//./gradlew :onyx-api:clean :onyx-api:build
