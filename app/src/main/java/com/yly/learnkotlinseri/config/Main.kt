package com.yly.learnkotlinseri.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * @author    yiliyang
 * @date      2022/3/7 下午1:39
 * @version   1.0
 * @since     1.0
 */
val format = Json {
    prettyPrint = true
    //忽略冒号，字符串可以不加冒号，数字可以加
    isLenient = true
    //反序列化的时候忽略不匹配的值
    ignoreUnknownKeys = true
    //忽略类型错误
    //1：当值不能为null时，反序列化为空，这个时候不会报错，而是用默认值,如果没默认值报错
    //2:当枚举类型不匹配时,也会使用默认值不会报错，但是如果没有默认值，也会报错
    coerceInputValues = true
    //默认情况下，默认值不会序列化，这里可以序列化默认值
    encodeDefaults = true
    //null不会序列化
    explicitNulls = false
    //可以将map序列化为json数组吗，格式是[key1, value1, key2, value2,...]
    allowStructuredMapKeys = true
    //支持NaN等浮点数
    allowSpecialFloatingPointValues = true
    //设置多态的时候的type字段名称，相当于将type换成设定值
    classDiscriminator = "#class"
}

enum class MyEnum {
    FRUIT
}

@Serializable
//和  classDiscriminator = "#class"效果一样，这个值声明在父类，不同的子类不能取不同的名字
@JsonClassDiscriminator("message_type")
data class Project(
    val name: String,
    val language: String = "Kotlin",
    val myEnum: MyEnum = MyEnum.FRUIT,
    val age: Int? = null
)

fun main() {
    decodeValue()
}

fun decodeValue() {
    val data = format.decodeFromString<Project>(
        """
        {"name":"kotlinx.serialization","language":null,"myEnum":"FRUITS"}
    """
    )
    println(data)
}


/**
 * @JsonNames取别名，l和language同时有效
 */
//@Serializable
//data class Project(val name: String, @JsonNames("l") val language: String)
//
//fun main() {
//    val data = Project("kotlinx.serialization", "Kotlin")
//    println(format.encodeToString(data))
//}