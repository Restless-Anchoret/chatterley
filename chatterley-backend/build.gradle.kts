import com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
//import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    id("com.netflix.dgs.codegen") version "5.11.1"
    id("org.springframework.boot") version "3.0.0"
}

group = "com.ran"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    mavenLocal()
}

val dgsPlatformVersion = "6.0.0"
//val mongodbVersion = "5.1.0"
val postgresqlDriverVersion = "42.7.5"
val h2DriverVersion = "2.3.232"
val apacheCodecsVersion = "1.16.0"
val jwtVersion = "0.12.6"

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:$dgsPlatformVersion"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("com.netflix.graphql.dgs:graphql-dgs-pagination")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("jakarta.annotation:jakarta.annotation-api")
//    implementation("net.datafaker:datafaker:1.7.0")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.postgresql:postgresql:$postgresqlDriverVersion")
//    implementation("org.mongodb:mongodb-driver-sync:$mongodbVersion")
//    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("commons-codec:commons-codec:$apacheCodecsVersion")
    implementation("io.jsonwebtoken:jjwt:$jwtVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("com.h2database:h2:$h2DriverVersion")
}

tasks.withType<GenerateJavaTask> {
	packageName = "com.ran.chatterley.graphql.generated"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
//	testLogging {
//		events(FAILED, STANDARD_ERROR, SKIPPED)
//		exceptionFormat = FULL
//		showExceptions = true
//		showCauses = true
//		showStackTraces = true
//	}
}
