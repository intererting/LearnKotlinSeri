package com.yly.learnkotlinseri

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/4 下午4:03
 * @version   1.0
 * @since     1.0
 */
@Serializable
data class BackField(val age: Int) {

    //这种情况没有backField，所以不会序列化
    val name: String
        get() = "default"

    @EncodeDefault
    val no: Int = 1

    //这种情况没有backField，所以不会序列化
    var height: Double
        get() = 1.0
        set(value) {

        }
}

fun main() {
    println(Json.encodeToString(BackField(12).apply {
        height = 100.0
    }))
}
