package com.yly.learnkotlinseri.endecoder

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

/**
 * @author    yiliyang
 * @date      2022/3/8 上午9:47
 * @version   1.0
 * @since     1.0
 */
@Serializable
data class CstmEncoderData(val name: String, val age: Int?, val lists: List<Int>)

class ListEncoder : AbstractEncoder() {
    val list = mutableListOf<Any>()

    override val serializersModule: SerializersModule = EmptySerializersModule

    override fun encodeValue(value: Any) {
        //这个地方是值
        list.add(value)
    }

    override fun beginCollection(
        descriptor: SerialDescriptor,
        collectionSize: Int
    ): CompositeEncoder {
        encodeInt(collectionSize)
        return this
    }

    override fun encodeNull() = encodeValue("NULL")
    override fun encodeNotNullMark() = encodeValue("!!")

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (serializer.descriptor == serializer<ByteArray>().descriptor)
            encodeByteArray(value as ByteArray)
        else
            super.encodeSerializableValue(serializer, value)
    }

    private fun encodeByteArray(bytes: ByteArray) {
        encodeCompactSize(bytes.size)
        output.write(bytes)
    }

    private fun encodeCompactSize(value: Int) {
        if (value < 0xff) {
            output.writeByte(value)
        } else {
            output.writeByte(0xff)
            output.writeInt(value)
        }
    }
}

val output = DataOutputStream(ByteArrayOutputStream())

inline fun <reified T> encodeToList(value: T) = encodeToList(serializer(), value)


fun <T> encodeToList(serializer: SerializationStrategy<T>, value: T): List<Any> {
    val encoder = ListEncoder()
    encoder.encodeSerializableValue(serializer, value)
    return encoder.list
}

fun main() {
//    println(encodeToList("1"))
    val cstmEncoderData = CstmEncoderData("yu", null, listOf(1))
    println(encodeToList(cstmEncoderData))
}




























