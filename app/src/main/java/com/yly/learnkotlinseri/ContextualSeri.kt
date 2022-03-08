package com.yly.learnkotlinseri

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.util.*

/**
 * @author    yiliyang
 * @date      2022/3/7 上午10:22
 * @version   1.0
 * @since     1.0
 */
@Serializable
class ContextualSeri(
    val name: String,
    @Contextual
    val date: Date
)

private val module = SerializersModule {
    contextual(DateAsLongSerializer)
}

val correctModule = SerializersModule {
    // args[0] contains Int.serializer() or String.serializer(), depending on the usage
    contextual(GenericBox::class) { args -> GenericSeri(args[0]) }
}

fun main() {
    val formater = Json { serializersModule = module }
    val contextualSeri = ContextualSeri("haha", Date())
    println(formater.encodeToJsonElement(contextualSeri))
}










