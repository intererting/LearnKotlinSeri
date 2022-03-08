package com.yly.learnkotlinseri.unsign

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/8 上午11:17
 * @version   1.0
 * @since     1.0
 */
@Serializable
class Counter(val counted: UByte, val description: String)

fun main() {
    val counted = 239.toUByte()
    println(Json.encodeToString(Counter(counted, "tries")))
}