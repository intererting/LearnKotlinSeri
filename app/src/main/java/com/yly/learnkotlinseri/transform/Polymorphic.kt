package com.yly.learnkotlinseri.transform

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject


/**
 * @author    yiliyang
 * @date      2022/3/7 下午2:56
 * @version   1.0
 * @since     1.0
 */
@kotlinx.serialization.Serializable
abstract class Projects {
    abstract val name: String
}

@kotlinx.serialization.Serializable
data class BasicProject(override val name: String) : Projects()


@kotlinx.serialization.Serializable
data class OwnedProject(override val name: String, val owner: String) : Projects()

object ProjectsSerializer : JsonContentPolymorphicSerializer<Projects>(Projects::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "owner" in element.jsonObject -> OwnedProject.serializer()
        else -> BasicProject.serializer()
    }
}

fun main() {
    val data = listOf(
        OwnedProject("kotlinx.serialization", "kotlin"),
        BasicProject("example")
    )
    val string = Json.encodeToString(ListSerializer(ProjectsSerializer), data)
    println(string)
    println(Json.decodeFromString(ListSerializer(ProjectsSerializer), string))
}