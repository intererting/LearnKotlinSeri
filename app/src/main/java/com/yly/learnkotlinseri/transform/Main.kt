package com.yly.learnkotlinseri.transform

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

/**
 * @author    yiliyang
 * @date      2022/3/7 下午2:06
 * @version   1.0
 * @since     1.0
 */
@kotlinx.serialization.Serializable
data class Project(
    val name: String,
    @kotlinx.serialization.Serializable(with = UserListSerializer::class)
    val users: List<User>
)

@Serializable
data class User(val name: String)

object UserListSerializer :
    JsonTransformingSerializer<List<User>>(ListSerializer(User.serializer())) {
    // If response is not an array, then it is a single object that should be wrapped into the array
    //将json转成List类型
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) JsonArray(listOf(element)) else element

    override fun transformSerialize(element: JsonElement): JsonElement {
        require(element is JsonArray) // this serializer is used only with lists
        //强行将List对象，转成{}
        return element.singleOrNull() ?: element
    }
}

/**
 * 过滤序列化属性
 */
object ProjectSerializer : JsonTransformingSerializer<Project>(Project.serializer()) {
    override fun transformSerialize(element: JsonElement): JsonElement {
        println(element.jsonObject)
        return JsonObject(element.jsonObject.filter { (k, v) ->
            k == "name" && v.jsonPrimitive.content == "yu"
        })
    }
}

fun main() {
//    transformDeSeri()
//    transformSeri()
    transformFilter()

}

fun transformFilter() {
    val data = Project(name = "yuy", listOf(User("li")))
    println(Json.encodeToString(ProjectSerializer, data))
}

fun transformSeri() {
    val data = Project("kotlinx.serialization", listOf(User("kotlin")))
    println(Json.encodeToString(data))
}

fun transformDeSeri() {
    println(
        Json.decodeFromString<Project>(
            """
        {"name":"kotlinx.serialization","users":{"name":"kotlin"}}
    """
        )
    )
    println(
        Json.decodeFromString<Project>(
            """
        {"name":"kotlinx.serialization","users":[{"name":"kotlin"},{"name":"jetbrains"}]}
    """
        )
    )
}
