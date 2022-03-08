package com.yly.learnkotlinseri.inline

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/8 上午11:15
 * @version   1.0
 * @since     1.0
 */
@Serializable
@JvmInline
value class Color(val rgb: Int)

@Serializable
data class NamedColor(val color: Color, val name: String)

fun main() {
    println(Json.encodeToString(NamedColor(Color(0), "black")))
}