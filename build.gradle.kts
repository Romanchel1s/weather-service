plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("redis.clients:jedis:5.1.4")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("org.example.Main")
}

tasks.test {
    useJUnitPlatform()
}

// Настройка fat JAR
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("app")           // имя JAR
    archiveClassifier.set("")            // без classifier
    archiveVersion.set("1.0.0")         // версия в имени JAR
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}

// Чтобы ./gradlew build собирал shadow JAR вместо обычного
tasks.build {
    dependsOn(tasks.shadowJar)
}
