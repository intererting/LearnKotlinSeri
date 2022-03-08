package com.yly.learnkotlinseri.composite

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json

/**
 * @author    yiliyang
 * @date      2022/3/8 上午11:22
 * @version   1.0
 * @since     1.0
 */
object ColorAsObjectSerializer : KSerializer<Color> {
    override fun deserialize(decoder: Decoder): Color {
        return decoder.decodeStructure(descriptor) {
            var r = -1
            var g = -1
            var b = -1
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> r = decodeIntElement(descriptor, 0)
                    1 -> g = decodeIntElement(descriptor, 1)
                    2 -> b = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            require(r in 0..255 && g in 0..255 && b in 0..255)
            Color((r shl 16) or (g shl 8) or b)
        }
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("Color") {
            element<Int>("r")
            element<Int>("g")
            element<Int>("b")
        }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeStructure(descriptor) {
//            encodeInlineElement() //内联类
            encodeIntElement(descriptor, 0, (value.rgb shr 16) and 0xff)
            encodeIntElement(descriptor, 1, (value.rgb shr 8) and 0xff)
            encodeIntElement(descriptor, 2, value.rgb and 0xff)
        }
    }
}


fun main() {
    val color = Color(0x00ff00)
    val string = Json.encodeToString(color)
    println(string)
    println(Json.decodeFromString<Color>(string))
}