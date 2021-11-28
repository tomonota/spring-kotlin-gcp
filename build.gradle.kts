import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.3.72"
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.appengine") version "2.4.2"
}

group = "me.tomono"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencyManagement {
    dependencies {
        imports {
            mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:2.0.6")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.google.cloud:spring-cloud-gcp-starter-sql-mysql")
    implementation("com.google.firebase:firebase-admin:8.1.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("mysql:mysql-connector-java")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.appengineStage {
    doLast {
        copy {
            from("build/libs/" + rootProject.name + "-1.0-SNAPSHOT.jar")
            into("build/staged-app")
        }
        delete("build/staged-app/" + rootProject.name + "-1.0-SNAPSHOT-plain.jar")
    }
}

appengine {
    deploy {
        // Google Cloud Project ID
        projectId = ""
        // デプロイによって反映される Web アプリのバージョン
        // 指定しなければ新しく生成される
        version = "GCLOUD_CONFIG"
    }
}