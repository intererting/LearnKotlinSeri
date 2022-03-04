package com.yly.learnkotlinseri

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/4 下午3:50
 * @version   1.0
 * @since     1.0
 */
@Serializable
object SerializationVersion {
    @EncodeDefault
    var libraryVersion: String = "1.0.0"
}

fun main() {
    //单例或者Unit都是{}
    println(Json.encodeToString(SerializationVersion))
    println(Json.encodeToString(Unit))
}