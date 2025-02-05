println("Charset: ${System.out.charset()}")
var minecraftVersion="1.21.4"

allprojects {
    version = "1.3"
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}

tasks.register("build") {
    dependsOn(":hypertension-fabric:build")
    dependsOn(":hypertension-spigot:build")
    doLast {
        file("hypertension-fabric/build/libs/Hypertension-fabric-${version}.jar").copyTo(
            file("build/libs/Hypertension-fabric-${version}-${minecraftVersion}.jar"), true
        )
        file("hypertension-spigot/build/libs/Hypertension-spigot-${version}.jar").copyTo(
            file("build/libs/Hypertension-spigot-${version}-${minecraftVersion}.jar"), true
        )
    }
}