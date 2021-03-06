/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.5/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the groovy plugin to also add support for Groovy (needed for Spock)
    groovy

    // Apply Spring Boot Gradle Plugin to provide Spring Boot support
    id("org.springframework.boot") version "2.3.1.RELEASE"

    // Apply OpenAPI Generator Plugin to generate API server stubs, documentation and configuration automatically given an OpenAPI Spec v3
    id("org.openapi.generator") version "4.3.1"

    // Extends the Gradle's "idea" DSL with specific settings: code style, facets, run configurations etc.
    id("org.jetbrains.gradle.plugin.idea-ext") version "0.7"

    // Automatic lombok and delombok configuration
    id("io.freefair.lombok") version "5.1.0"
}

description = "A Web API"
version = "1.0.0"

// Import the spring-boot-dependencies bom
apply(plugin = "io.spring.dependency-management")

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Add Keycloak Adapter BOM dependency
    implementation(platform("org.keycloak.bom:keycloak-adapter-bom:10.0.2"))

    implementation("org.keycloak:keycloak-spring-boot-starter") {
        exclude(group = "org.springframework.boot",  module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot",  module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("io.swagger:swagger-models:1.6.1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")
    implementation("org.mapstruct:mapstruct:1.3.1.Final")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("com.h2database:h2")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.3.1.Final")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.security:spring-security-test")
    // Use the latest Groovy version for Spock testing
    testImplementation("org.codehaus.groovy:groovy-all:2.5.11")
    // Use the Spock testing and specification framework
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
    testImplementation("org.spockframework:spock-spring:1.3-groovy-2.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
}

java {
    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.VERSION_14
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
    source("${buildDir}/generated/open-api/src/main/java")
}

springBoot {
    mainClassName = "com.example.webapi.OpenAPI2SpringBoot"

    // Generate Build Information used by Actuator /info endpoint
    buildInfo()
}

sourceSets {
    main {
        java {
            srcDir("${buildDir.absolutePath}/generated/open-api/src/main/java")
        }
    }
}

sourceSets.configureEach {
    tasks.named<JavaCompile>(compileJavaTaskName) {
        options.annotationProcessorGeneratedSourcesDirectory = file("$buildDir/generated/sources/annotationProcessor/java/${this@configureEach.name}")
    }
}

// Add attributes to the generated jar manifest
tasks.bootJar {
    manifest {
        attributes(
                mapOf("Implementation-Title" to project.name,
                        "Implementation-Version" to project.version)
        )
    }
    // Package a Layered Jar to build a docker image
    layered()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("${projectDir}/src/main/resources/api/openapi.yaml")
    outputDir.set("${buildDir}/generated/open-api")
    invokerPackage.set("com.example.webapi")
    apiPackage.set("com.example.webapi.api")
    modelPackage.set("com.example.webapi.model")
    modelNameSuffix.set("Dto")
    configOptions.put("configPackage", "com.example.webapi.config")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("delegatePattern", "true")
    configOptions.put("performBeanValidation", "true")
    configOptions.put("swaggerDocketConfig", "true")
    configOptions.put("useTags", "true")
}

fun Project.idea(block: org.gradle.plugins.ide.idea.model.IdeaModel.() -> Unit) =
        (this as ExtensionAware).extensions.configure("idea", block)

fun org.gradle.plugins.ide.idea.model.IdeaProject.settings(block: org.jetbrains.gradle.ext.ProjectSettings.() -> Unit) =
        (this@settings as ExtensionAware).extensions.configure(block)

fun org.jetbrains.gradle.ext.ProjectSettings.taskTriggers(block: org.jetbrains.gradle.ext.TaskTriggersConfig.() -> Unit) =
        (this@taskTriggers as ExtensionAware).extensions.configure("taskTriggers", block)

rootProject.idea {
    project {
        settings {
            taskTriggers {
                beforeBuild(openApiGenerate)
                beforeRebuild(openApiGenerate)
                afterSync(openApiGenerate)
                beforeSync(openApiGenerate)
            }
        }
    }
}