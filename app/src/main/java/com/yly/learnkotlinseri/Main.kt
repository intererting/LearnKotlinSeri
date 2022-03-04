package com.yly.learnkotlinseri

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/4 下午1:59
 * @version   1.0
 * @since     1.0
 */
@Serializable
class Project(
    val name: String,
    @Transient val language: String = "ch",
    val country: String? = null
) {
    //只有当不是默认值的时候才序列化，没有修改值就不会序列化
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName("changeAge")
    var age: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Project(name='$name', language='$language', country=$country, age=$age)"
    }
}

fun main() {
//    test1()
    test2()
}

fun test2() {
    val list = listOf(
        Project("kotlinx.serialization"),
        Project("kotlinx.coroutines")
    )
    println(Json.encodeToString(list))
}

fun test1() {
    val data = Project("kotlinx.serialization", "Kotlin").apply {
        age = 10
    }
    println(Json {
        //可以不序列化null
        explicitNulls = false
        //如果是null则会序列化为null
        encodeDefaults = true
    }.encodeToString(data))
}

fun test() {
    val data = Json.decodeFromString<Project>(
        """
        {"name":"kotlinx.serialization","age":30}
    """
    )
    println(data)
}
