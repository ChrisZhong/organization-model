import java.time.Instant

val moduleName = "runtimemodels.chazm.model"

plugins {
    `java-library`
    jacoco
    `maven-publish`
    signing
    id("com.jfrog.bintray")
}

repositories {
    jcenter()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

group = rootProject.group
version = "${rootProject.version}.0.0"

dependencies {
    annotationProcessor(library.lombok)
    compileOnly(library.lombok)
    implementation(library.springBoot.bom) // BOM
    implementation(library.guice.bom) // BOM
    implementation(project(":chazm-api"))
    implementation("org.slf4j:slf4j-api")
    implementation("javax:javaee-api:+")
    implementation("com.google.inject:guice")
    implementation("com.google.inject.extensions:guice-assistedinject")

    testImplementation(library.junit.bom) // BOM
    testImplementation(library.junit.jupiter.api)
    testImplementation(library.junit.jupiter.params)

    testRuntimeOnly(library.junit.jupiter.engine)

    testImplementation("org.jmockit:jmockit:+")
}

val sourceJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allJava)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourceJar.get())
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("mavenJava")
    dryRun = System.getenv("TRAVIS_BRANCH") != "master" || System.getenv("TRAVIS_PULL_REQUEST") != "false"
    publish = System.getenv("TRAVIS_BRANCH") == "master" && System.getenv("TRAVIS_PULL_REQUEST") == "false"

    pkg.repo = "maven"
    pkg.name = "chazm"
    pkg.userOrg = "runtimemodels"
    pkg.desc = "An organizational runtime model"
    pkg.websiteUrl = "https://runtimemodels.github.io/chazm/"
    pkg.issueTrackerUrl = "https://github.com/runtimemodels/chazm/issues"
    pkg.vcsUrl = "https://github.com/RuntimeModels/chazm.git"
    pkg.setLicenses("Apache-2.0")
    pkg.githubRepo = "RuntimeModels/chazm"
    pkg.githubReleaseNotesFile = "CHANGES.md"
    pkg.version.name = "${project.version}"
    pkg.version.desc = "Chazm"
    pkg.version.released = Instant.now().toString()
}

tasks {
    named("compileJava", JavaCompile::class) {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf("--module-path", classpath.asPath)
            classpath = files()
        }
    }
    named("compileTestJava", JavaCompile::class) {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--add-modules", "junit",
                    "--add-reads", "$moduleName=junit",
                    "--patch-module", "$moduleName=" + files(sourceSets["test"].java.srcDirs).asPath
            )
            classpath = files()
        }
    }
    named("test", Test::class) {
        inputs.property("moduleName", moduleName)
        doFirst {
            jvmArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--add-modules", "ALL-MODULE-PATH",
                    "--add-reads", "$moduleName=junit",
                    "--patch-module", "$moduleName=" + files(sourceSets["test"].java.outputDir).asPath
            )
            classpath = files()
        }
    }
    withType(JacocoReport::class) {
        reports {
            csv.isEnabled = false
            xml.isEnabled = true
            html.isEnabled = System.getenv("CI").isNullOrBlank()
        }
    }
}