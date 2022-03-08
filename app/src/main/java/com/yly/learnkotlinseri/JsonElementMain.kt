package com.yly.learnkotlinseri

import kotlinx.serialization.json.*

/**
 * @author    yiliyang
 * @date      2022/3/7 下午2:02
 * @version   1.0
 * @since     1.0
 */
fun main() {
//    parseJsonElement()
    buildJsonElement()
}

fun buildJsonElement() {
    val element = buildJsonObject {
        put("name", "kotlinx.serialization")
        putJsonObject("owner") {
            put("name", "kotlin")
        }
        putJsonArray("forks") {
            addJsonObject {
                put("votes", 42)
            }
            addJsonObject {
                put("votes", 9000)
            }
        }
    }
    println(element)
}

fun parseJsonElement() {
    val element = Json.parseToJsonElement(
        """
        {
            "name": "kotlinx.serialization",
            "forks": [{"votes": 42}, {"votes": 9000}, {}]
        }
    """
    )
    val sum = element
        .jsonObject["forks"]!!
        .jsonArray.sumOf { it.jsonObject["votes"]?.jsonPrimitive?.int ?: 0 }
    println(sum)
}
