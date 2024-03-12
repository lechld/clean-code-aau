plugins {
    id("java")
    kotlin("jvm")
}

group = "aau.edu.dolechl.cleancode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.4")

    // jsoup HTML parser library @ https://jsoup.org/
    implementation("org.jsoup:jsoup:1.17.2")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}