println("Charset: ${System.out.charset()}")

allprojects {
    version = "1.0"
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}

tasks.register("build") {
    dependsOn(":hypertension-fabric:build")
    dependsOn(":hypertension-spigot:build")
    doLast {
        file("hypertension-fabric/build/libs/Hypertension-fabric-${version}.jar").copyTo(
            file("build/libs/Hypertension-fabric-${version}.jar"), true
        )
        file("hypertension-spigot/build/libs/Hypertension-spigot-${version}.jar").copyTo(
            file("build/libs/Hypertension-spigot-${version}.jar"), true
        )
    }
}