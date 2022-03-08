package com.yly.learnkotlinseri.endecoder

import com.yly.learnkotlinseri.transform.User
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer

/**
 * @author    yiliyang
 * @date      2022/3/8 上午10:04
 * @version   1.0
 * @since     1.0
 */
class ListDecoder(val list: ArrayDeque<Any>, var elementsCount: Int = 0) : AbstractDecoder() {
    private var elementIndex = 0

    override val serializersModule: SerializersModule = EmptySerializersModule

    override fun decodeValue(): Any = list.removeFirst()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (elementIndex == elementsCount) return CompositeDecoder.DECODE_DONE
        return elementIndex++
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder =
        ListDecoder(list, descriptor.elementsCount)

    override fun decodeSequentially(): Boolean = true

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int =
        //列表的大小，会被序列化
        decodeInt().also { elementsCount = it }

//    override fun decodeNull(): Nothing? {
//        return null
//    }

    override fun decodeNotNullMark(): Boolean {
        return decodeString() != "NULL"
    }
}

fun <T> decodeFromList(list: List<Any>, deserializer: DeserializationStrategy<T>): T {
    val decoder = ListDecoder(ArrayDeque(list))
    return decoder.decodeSerializableValue(deserializer)
}

inline fun <reified T> decodeFromList(list: List<Any>): T = decodeFromList(list, serializer())

fun main() {
    val data = CstmEncoderData("yu", null, listOf(1, 2, 3))
    val list = encodeToList(data)
    println(list)
    val obj = decodeFromList<CstmEncoderData>(list)
    println(obj)
}