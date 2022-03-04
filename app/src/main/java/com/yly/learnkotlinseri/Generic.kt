package com.yly.learnkotlinseri

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/4 下午2:40
 * @version   1.0
 * @since     1.0
 */
@Serializable
class Box<T>(val contents: T)

@Serializable
class Data(
    val a: Box<Int>,
    val b: Box<Project>
)

fun main() {
    val data = Data(Box(42), Box(Project("kotlinx.serialization", "Kotlin")))
    println(Json.encodeToString(data))
}