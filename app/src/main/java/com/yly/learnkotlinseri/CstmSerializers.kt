@file:UseSerializers(DateAsLongSerializer::class)

package com.yly.learnkotlinseri

import kotlinx.serialization.*
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import java.util.*

/**
 * @author    yiliyang
 * @date      2022/3/4 下午4:10
 * @version   1.0
 * @since     1.0
 */

@Serializable(with = ColorAsStringSerializer::class)
@SerialName("Color")
class Color(val rgb: Int)

@Serializable
class IncludeColor(val color: Color)

fun main() {
    //获取序列化器
//    val stringToColorMapSerializer: KSerializer<Map<String, Color>> = serializer()
//    println(stringToColorMapSerializer.descriptor)

//    val green = Color(0x00ff00)
//    println(Json.encodeToString(green))

//    val color = Json.decodeFromString<Color>("\"00ff00\"")
//    println(color.rgb) // prints 65280

//    val includeColor = IncludeColor(Color(65280))
//    println(Json.encodeToString(includeColor))

    println(encodeToString(DateAsLongSerializer, Date()))

}

object ColorAsStringSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Color) {
        val string = value.rgb.toString(16).padStart(6, '0')
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Color {
        val string = decoder.decodeString()
        return Color(string.toInt(16))
    }
}

class ColorIntArraySerializer : KSerializer<Color> {
    private val delegateSerializer = IntArraySerializer()
    override val descriptor = SerialDescriptor("Color", delegateSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: Color) {
        val data = intArrayOf(
            (value.rgb shr 16) and 0xFF,
            (value.rgb shr 8) and 0xFF,
            value.rgb and 0xFF
        )
        encoder.encodeSerializableValue(delegateSerializer, data)
    }

    override fun deserialize(decoder: Decoder): Color {
        val array = decoder.decodeSerializableValue(delegateSerializer)
        return Color((array[0] shl 16) or (array[1] shl 8) or array[2])
    }
}

object DateAsLongSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
    override fun deserialize(decoder: Decoder): Date = Date(decoder.decodeLong())
}