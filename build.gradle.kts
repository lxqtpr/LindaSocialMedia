plugins {
	java
	id("org.springframework.boot") version "3.2.0-M3"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "dev.lxqtpr"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")

	// https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")

	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-oauth2-resource-server
	implementation("org.springframework.security:spring-security-oauth2-resource-server:6.1.2")

	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-oauth2-jose
	implementation("org.springframework.security:spring-security-oauth2-jose:6.1.2")

	// https://mvnrepository.com/artifact/com.nimbusds/nimbus-jose-jwt
	implementation("com.nimbusds:nimbus-jose-jwt:9.31")

	// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
	implementation("org.hibernate:hibernate-core:6.2.5.Final")

	// https://mvnrepository.com/artifact/org.springframework.data/spring-data-relational
	implementation("org.springframework.data:spring-data-relational:3.1.3")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.minio:minio:8.5.5")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.4")
	implementation("org.flywaydb:flyway-core:9.22.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springframework.boot:spring-boot-starter-websocket:3.1.4")
	implementation("org.modelmapper:modelmapper:3.1.1")
	testImplementation("junit:junit:4.13.1")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
