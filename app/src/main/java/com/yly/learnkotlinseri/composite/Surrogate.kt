package com.yly.learnkotlinseri.composite

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/8 上午11:25
 * @version   1.0
 * @since     1.0
 */
//@Serializable(with = ColorSerializer::class)
@Serializable(with = ColorAsObjectSerializer::class)
class Color(val rgb: Int)

@Serializable
@SerialName("Color")
private class ColorSurrogate(val r: Int, val g: Int, val b: Int) {
    init {
        require(r in 0..255 && g in 0..255 && b in 0..255)
    }
}

object ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor = ColorSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Color) {
        val surrogate = ColorSurrogate(
            (value.rgb shr 16) and 0xff,
            (value.rgb shr 8) and 0xff,
            value.rgb and 0xff
        )
//        encoder.encodeInline()
        encoder.encodeSerializableValue(ColorSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): Color {
        val surrogate = decoder.decodeSerializableValue(ColorSurrogate.serializer())
        return Color((surrogate.r shl 16) or (surrogate.g shl 8) or surrogate.b)
    }
}

fun main() {
    //通过代理方式将结果转化成{"r":0,"g":255,"b":0}
    val color = Color(65280)
    println(Json.encodeToString(color))
}