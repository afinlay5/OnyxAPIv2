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

	implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2") // Only JsonDeserializer
	implementation("com.google.guava:guava:32.0.0-jre")
	implementation("commons-io:commons-io:2.8.0")
	implementation("de.siegmar:fastcsv:2.2.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("org.apache.tika:tika-core:2.7.0")
	implementation("org.springframework:spring-context:6.0.9")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}