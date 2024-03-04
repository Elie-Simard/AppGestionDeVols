plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Remove the duplicate line and keep just one
    implementation("org.apache.aries.application:application-modeller-standalone:1.0.0")

    // Add MySQL JDBC Driver dependency
    implementation("mysql:mysql-connector-java:8.0.28") // Adjust the version as needed

    // JUnit 5 dependencies
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
