package com.yly.learnkotlinseri.protobuf

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoIntegerType
import kotlinx.serialization.protobuf.ProtoNumber
import kotlinx.serialization.protobuf.ProtoType
import kotlinx.serialization.protobuf.schema.ProtoBufSchemaGenerator

/**
 * @author    yiliyang
 * @date      2022/3/7 下午5:27
 * @version   1.0
 * @since     1.0
 */
@Serializable
data class Project(


    @ProtoNumber(1)
    val name: String,

    @ProtoNumber(2)
    val language: String,

    @ProtoNumber(3)
//    The default is a varint encoding (intXX) that is optimized for small non-negative numbers. The value of 1 is encoded in one byte 01.
//The signed is a signed ZigZag encoding (sintXX) that is optimized for small signed integers. The value of -2 is encoded in one byte 03.
//The fixed encoding (fixedXX) always uses a fixed number of bytes. The value of 3 is encoded as four bytes 03 00 00 00.
    @ProtoType(ProtoIntegerType.DEFAULT)
    val age: Int
)

@Serializable
data class Data(
    val a: List<Int> = emptyList(),
    val b: List<Int> = emptyList()
)

@Serializable
data class SampleData(
    val amount: Long,
    val description: String?,
    val department: String = "QA"
)

fun main() {
//    basic()
//    listTest()
    generateProtoFile()
}

fun generateProtoFile() {
    val descriptors = listOf(SampleData.serializer().descriptor)
    val schemas = ProtoBufSchemaGenerator.generateSchemaText(descriptors)
    println(schemas)
}

fun listTest() {
    val data = Data(listOf(1, 2, 3), listOf())
    val bytes = ProtoBuf.encodeToByteArray(data)
    println(bytes.joinToString {
        it.toString(16)
    })
    println(ProtoBuf.decodeFromByteArray<Data>(bytes))
}

fun basic() {
    val data = Project("kotlinx.serialization", "Kotlin", 1)
    val bytes = ProtoBuf.encodeToByteArray(data)
    val obj = ProtoBuf.decodeFromByteArray<Project>(bytes)
    println(obj)
}
