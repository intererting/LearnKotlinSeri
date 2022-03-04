package com.yly.learnkotlinseri

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * @author    yiliyang
 * @date      2022/3/4 下午3:36
 * @version   1.0
 * @since     1.0
 */
@Serializable
enum class Status {
    //默认枚举是枚举字段值，但是可以修改值
    @SerialName("changedName")
    SUPPORTED
}

@Serializable
data class TypeData(
    @Serializable(with = LongAsStringSerializer::class)
    val signature: Long,
    val status: Status,
    var list: List<String>
)

fun main() {
    testMap()
}

fun testEnum() {
    val data = TypeData(0x1CAFE2FEED0BABE0, Status.SUPPORTED, listOf("1"))
    println(Json.encodeToString(data))
}

fun testList() {
    val data = Json.decodeFromString<TypeData>(
        """
        {
            "signature":"1",
            "status":"changedName",
            "list":["1","2"]
        }
    """
    )
    println(data)
}

fun testMap() {
    //map的序列化，key总是String
    val map = mapOf(
        1 to Project("kotlinx.serialization"),
        2 to Project("kotlinx.coroutines")
    )
    println(Json.encodeToString(map))
}
