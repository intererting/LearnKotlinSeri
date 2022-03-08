package com.yly.learnkotlinseri

import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/7 上午10:33
 * @version   1.0
 * @since     1.0
 */
// NOT @Serializable, will use external serializer
class ThirdProject(
    // val in a primary constructor -- serialized
    val name: String
) {
    var stars: Int = 0 // property with getter & setter -- serialized

    val path: String // getter only -- not serialized
        get() = "kotlin/$name"

    private var locked: Boolean = false // private, not accessible -- not serialized
}

@Serializer(forClass = ThirdProject::class)
object ProjectSerializer

fun main() {
    val data = ThirdProject("kotlinx.serialization").apply { stars = 9000 }
    println(Json.encodeToString(ProjectSerializer, data))
}