plugins {
    `build-scan`
    base
    eclipse
    idea

//    id "com.github.kt3k.coveralls" version "2.8.1"
//    id "org.sonarqube" version "2.5"
//    id "org.standardout.versioneye" version "1.5.0"

//    id "io.spring.dependency-management" version "1.0.3.RELEASE" apply false
//    id "com.jfrog.bintray" version "1.7.3" apply false
}

buildScan {
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")
}

//sonarqube {
//    properties {
//        property "sonar.host.url", "https://sonarqube.com"
//        property "sonar.projectKey", "chazm"
//        property "sonar.projectName", "Chazm: An Organizational Multiagent Runtime Model"
//        property "sonar.projectVersion", "${project.version}"
//        property "sonar.login", System.getenv("SONAR_TOKEN")
//        if (System.getenv("TRAVIS_BRANCH") == "master") {
//            if (System.getenv("TRAVIS_PULL_REQUEST") == "false") {
//            } else {
//                property "sonar.analysis.mode", "preview"
//                property "sonar.github.pullRequest", System.getenv("TRAVIS_PULL_REQUEST")
//                property "sonar.github.repository", System.getenv("TRAVIS_REPO_SLUG")
//                property "sonar.github.oauth", System.getenv("GITHUB_TOKEN")
//            }
//        } else {
//            property "sonar.analysis.mode", "issues"
//        }
//    }
//}
//
//versioneye {
//    includeSubProjects = true
//}
//
//ext {
//    hash = System.getenv("TRAVIS_COMMIT") == null ? "SNAPSHOT" : "git rev-parse --short ${System.getenv("TRAVIS_COMMIT")}".execute().text.trim()
//}
//
//subprojects {
//    apply plugin: "java"
//    apply plugin: "maven-publish"
//    apply plugin: "jacoco"
//    apply plugin: "io.spring.dependency-management"
//    apply plugin: "com.jfrog.bintray"
//
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//
//    group rootProject.group
//
//    version "${rootProject.version}-${hash}"
//
//    repositories {
//        jcenter()
//    }
//
//    dependencyManagement {
//        dependencies {
//            dependency "javax:javaee-api:${javaeeVersion}"
//            dependency "org.jmockit:jmockit:${jmockitVersion}"
//            dependency "junit:junit:${junitVersion}"
//            dependency "org.projectlombok:lombok:${lombokVersion}"
//        }
//
//        imports {
//            mavenBom "io.spring.platform:platform-bom:2.0.8.RELEASE"
//            mavenBom "com.google.inject:guice-bom:4.1.0"
//        }
//    }
//
//    jacocoTestReport {
//        reports {
//            xml {
//                enabled true
//            }
//        }
//    }
//
//    task sourceJar(type: Jar) {
//        classifier "sources"
//        from sourceSets.main.allJava
//    }
//
//    artifacts {
//        archives sourceJar
//    }
//
//    publishing {
//        publications {
//            maven(MavenPublication) {
//                from components.java
//                artifact sourceJar
//            }
//        }
//    }
//
//    bintray {
//        user = System.getenv("BINTRAY_USER")
//        key = System.getenv("BINTRAY_KEY")
//        publications = ["maven"]
//        dryRun = System.getenv("TRAVIS_BRANCH") != "master" || System.getenv("TRAVIS_PULL_REQUEST") != "false"
//        publish = System.getenv("TRAVIS_BRANCH") == "master" && System.getenv("TRAVIS_PULL_REQUEST") == "false"
//        pkg {
//            repo = "maven"
//            name = "chazm"
//            userOrg = "runtimemodels"
//            desc = "An organizational runtime model"
//            websiteUrl = "https://runtimemodels.github.io/chazm/"
//            issueTrackerUrl = "https://github.com/runtimemodels/chazm/issues"
//            vcsUrl = "https://github.com/RuntimeModels/chazm.git"
//            licenses = ["Apache-2.0"]
//
//            githubRepo = "RuntimeModels/chazm"
//            githubReleaseNotesFile = "CHANGES.md"
//
//            version {
//                name = "${project.version}"
//                desc = "Chazm "
//                released = new Date()
//            }
//        }
//    }
//}
//
//coveralls {
//    sourceDirs = subprojects.sourceSets.main.java.srcDirs.flatten()
//}
//
//task jacocoTestReport(type: JacocoReport) {
//    dependsOn subprojects.jacocoTestReport
//    jacocoClasspath = files(subprojects.configurations.jacocoAnt)
//    sourceDirectories = files(subprojects.sourceSets.main.java.srcDirs)
//    classDirectories = files(subprojects.sourceSets.main.output.classesDirs)
//    executionData = files(subprojects.jacocoTestReport.executionData).filter { it.exists() }
//    reports {
//        csv {
//            enabled false
//            destination file("${buildDir}/reports/jacoco/test/${task.name}.csv")
//        }
//        html {
//            enabled false
//            destination file("${buildDir}/reports/jacoco/test/html/")
//        }
//        xml {
//            enabled true
//            destination file("${buildDir}/reports/jacoco/test/${task.name}.xml")
//        }
//    }
//}
//
//task printInfo {
//    doLast {
//        println "  - project: ${project.name}"
//        println "  - ${subprojects.size()} subprojects:"
//        subprojects.each {
//            println "    - ${it.name}:"
//            println "      - archive artifacts: ${it.configurations.archives.allArtifacts.size()}"
//            it.configurations.archives.artifacts.files.each {
//                println "        - ${it.name}"
//            }
//            println "      - group: ${it.group}"
//            println "      - version: ${it.version}"
//        }
//        println "  - group: ${project.group}"
//        println "  - version: ${project.version}"
//    }
//}
//

tasks {
    withType(Wrapper::class) {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "4.10.1"
    }
}