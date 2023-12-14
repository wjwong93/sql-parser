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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}

application {
    mainClass = "SQLParser"
}

sourceSets {
    test {
        resources {
            srcDir("test")
        }
    }
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

        delete {
            file("src/main/java/PostgreSQLLexer.tokens")
            file("src/main/java/PostgreSQLLexer.interp")
            file("src/main/java/PostgreSQLParser.tokens")
            file("src/main/java/PostgreSQLParser.interp")
        }
    }
}