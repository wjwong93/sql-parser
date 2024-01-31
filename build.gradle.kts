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
    implementation("com.basho.riak:riak-client:2.1.1")
    implementation("com.arcadedb:arcadedb-engine:23.12.2")
    implementation("com.arcadedb:arcadedb-network:23.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}

application {
    mainClass = "com.wjwong93.polystore.Main"
    applicationDefaultJvmArgs += listOf(
            "-Xmx1536M",
            "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
            "--add-opens", "java.base/java.nio=ALL-UNNAMED",
            "--add-opens", "java.base/java.security=ALL-UNNAMED",
            "--add-opens", "jdk.unsupported/sun.misc=ALL-UNNAMED",
            "--add-opens", "java.base/sun.security.action=ALL-UNNAMED",
            "--add-opens", "jdk.naming.rmi/com.sun.jndi.rmi.registry=ALL-UNNAMED",
            "--add-opens", "java.base/sun.net=ALL-UNNAMED",
            "--add-opens", "java.base/java.lang=ALL-UNNAMED",
            "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED",
            "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED",
            "--add-opens", "java.base/java.io=ALL-UNNAMED"
    )
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
    mainClass = "com.wjwong93.polystore.parser.SQLParser"
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("leveldb_setup") {
    description = "Setup LevelDB database and import test data."
    mainClass = "com.wjwong93.polystore.LevelDBSetup"
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.test {
    useJUnitPlatform()
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-o", "../main", "-package", "com.wjwong93.polystore.parser")
    outputDirectory = file("src/main/java/com/wjwong93/polystore/parser")

    doLast {
        copy {
            from(file("src/main/java/com/wjwong93/polystore/parser/PostgreSQLLexer.tokens"))
            from(file("src/main/java/com/wjwong93/polystore/parser/PostgreSQLLexer.interp"))
            from(file("src/main/java/com/wjwong93/polystore/parser/PostgreSQLParser.tokens"))
            from(file("src/main/java/com/wjwong93/polystore/parser/PostgreSQLParser.interp"))
            into(file("src/main/antlr"))
        }

        val filesToDelete = project.fileTree("src/main/java/com/wjwong93/polystore/parser").matching {
            include("PostgreSQLLexer.tokens")
            include("PostgreSQLLexer.interp")
            include("PostgreSQLParser.tokens")
            include("PostgreSQLParser.interp")
        }

//        filesToDelete.files.forEach {
//            file -> file.delete()
//        }
    }
}