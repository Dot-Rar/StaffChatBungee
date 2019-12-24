plugins {
    java
}

group = "com.perkelle.dev.staffchatbungee"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url="https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.14-SNAPSHOT")
    implementation("redis.clients:jedis:3.2.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}