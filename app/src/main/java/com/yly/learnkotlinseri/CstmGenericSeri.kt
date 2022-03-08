package com.yly.learnkotlinseri

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/7 上午9:51
 * @version   1.0
 * @since     1.0
 */
@Serializable(with = GenericSeri::class)
class GenericBox<T>(val content: T)

class GenericSeri<T>(val contentSeri: KSerializer<T>) : KSerializer<GenericBox<T>> {
    override val descriptor: SerialDescriptor
        get() = contentSeri.descriptor

    override fun deserialize(decoder: Decoder): GenericBox<T> {
        return GenericBox(contentSeri.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: GenericBox<T>) {
        println(contentSeri.javaClass)
        contentSeri.serialize(encoder, value.content)
    }
}

fun main() {
    val genericBox = GenericBox("haha")
    println(Json.encodeToString(genericBox))

}










