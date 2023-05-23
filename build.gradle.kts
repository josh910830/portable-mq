import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    java
    `java-library`
    `maven-publish`
}

group = "com.github.josh910830"
version = "1.1.2"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    val kotestVersion = "5.6.1"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    val kotestSpringExtensionVersion = "1.1.3"
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringExtensionVersion")

    val mockkVersion = "1.13.5"
    testImplementation("io.mockk:mockk:$mockkVersion")

    val springMockkVersion = "4.0.2"
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    enabled = true
    archiveClassifier.set("")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<BootJar> {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.wrapper {
    gradleVersion = "7.6.1"
    distributionType = Wrapper.DistributionType.ALL
}
