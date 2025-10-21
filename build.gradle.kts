plugins {
    id("org.gradle.java-library")
    id("maven-publish")
}

version = "2.20.0"

val gwtVersion = "2.12.2"
val jacksonAnnotationVersion = "2.20"
val javapoetVersion = "1.13.0"

dependencies {
    implementation("org.gwtproject:gwt-user:$gwtVersion")
    implementation("org.gwtproject:gwt-dev:$gwtVersion")
    implementation("org.gwtproject:gwt-servlet:$gwtVersion")
    implementation("org.gwtproject:gwt-codeserver:$gwtVersion")

    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationVersion") {
        artifact { classifier = "sources" }
    }
    compileOnly("com.squareup:javapoet:$javapoetVersion")

    //testImplementation("junit:junit:4.13.2")
    //testImplementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
tasks.withType<JavaCompile>().configureEach {
    options.release = 17
}
tasks.withType<Wrapper> {
    gradleVersion = "9.1.0"
}
tasks.withType<JavaCompile>().configureEach {
    options.apply {
        isFork = true
        encoding = "UTF-8"
        compilerArgs.add("-implicit:none")
        compilerArgs.add("-Xlint:all,-serial,-rawtypes,-this-escape,-unchecked,-fallthrough")
    }
}
tasks.withType<Javadoc>().configureEach {
    options {
        this as StandardJavadocDocletOptions
        encoding = "UTF-8"
    }
}

repositories {
    mavenCentral()
}

configurations {
    runtimeClasspath {
        exclude(group = "org.gwtproject", module = "gwt-servlet")
        exclude(group = "org.gwtproject", module = "gwt-user")
        exclude(group = "org.gwtproject", module = "gwt-dev")
        exclude(group = "org.gwtproject", module = "gwt-codeserver")
    }
}

tasks.withType<Jar> {
    from(sourceSets.getByName("main").java)
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
