plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    // Spring REST Docs - asciidoctor
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.stress"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

val kotlinJdslVersion = "3.5.4"
// 스니팻 저장경로 선언
val snippetsDir = file("build/generated-snippets")
// Spring REST Docs 사용을 위한 asciidocterExt 변수 선언
val asciidoctorExt = configurations.create("asciidoctorExt") {
    extendsFrom(configurations.testImplementation.get())
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    // kotlin JDSL
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:$kotlinJdslVersion")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:$kotlinJdslVersion")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:$kotlinJdslVersion")
    // Spring REST Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    // Spring Data Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.test {
    // Spring REST Docs
    outputs.dir(snippetsDir)
    useJUnitPlatform()
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt)
    baseDirFollowsSourceFile() // index.adoc에 별도 adoc 파일을 import 하려면 해당 설정이 필요하다.
    dependsOn(tasks.test)
}

// ============= 문서 제공을 위한 커스텀 task 등록 ============= //
val copyDocument = tasks.register<Copy>("copyDocument") {
    // dependsOn: Task 실행 전 특정 Task를 실행하고 성공한 Task를 실행할 수 있게 하는 기능!
    dependsOn(tasks.asciidoctor)
    doFirst {
        delete(file("src/main/resources/static/docs"))
    }
    from(file("build/docs/asciidoc"))
    into(file("src/main/resources/static/docs"))
}

tasks.build {
    dependsOn(copyDocument)
}
// ============= 문서 제공을 위한 커스텀 task 등록 ============= //