plugins {
    java
    application

    antlr
}

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.reactivestreams:reactive-streams")
    implementation("org.neo4j.driver:neo4j-java-driver:5.12.0")
    implementation("org.fusesource.leveldbjni:leveldbjni-all:1.8")
    implementation("com.opencsv:opencsv:5.9")
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("org.slf4j:slf4j-nop:2.0.11")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}

application {
    mainClass = "SQLiteManager"
}

tasks.named("run") {
    description = "Execute a query."
}

sourceSets {
    test {
        resources {
            srcDir("test")
        }
    }
}

tasks.register<JavaExec>("parse") {
    group = "application"
    description = "Parse a query and output the extracted query fragments."
    mainClass = "SQLParser"
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("leveldb_setup") {
    description = "Setup LevelDB database and import test data."
    mainClass = "LevelDBSetup"
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.test {
    useJUnitPlatform()
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-o", "../main")
    outputDirectory = file("src/main/java")

    doLast {
        copy {
            from(file("src/main/java/PostgreSQLLexer.tokens"))
            from(file("src/main/java/PostgreSQLLexer.interp"))
            from(file("src/main/java/PostgreSQLParser.tokens"))
            from(file("src/main/java/PostgreSQLParser.interp"))
            into(file("src/main/antlr"))
        }

        val filesToDelete = project.fileTree("src/main/java").matching {
            include("PostgreSQLLexer.tokens")
            include("PostgreSQLLexer.interp")
            include("PostgreSQLParser.tokens")
            include("PostgreSQLParser.interp")
        }

        filesToDelete.files.forEach {
            file -> file.delete()
        }
    }
}