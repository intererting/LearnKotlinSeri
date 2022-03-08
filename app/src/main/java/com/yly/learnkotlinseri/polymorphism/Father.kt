package com.yly.learnkotlinseri.polymorphism

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * @author    yiliyang
 * @date      2022/3/7 上午10:41
 * @version   1.0
 * @since     1.0
 */
@Serializable
abstract class Father {
    abstract val name: String
}

@SerialName("son")
@Serializable
class Son(
    override val name: String,
//    @Polymorphic
    val age: Int
) : Father()

//@Serializable
//sealed class Father {
//    abstract val name: String
//
//    @EncodeDefault
//    var status: String = "open"//继承字段
//}

//@Serializable
//@SerialName("son")//这个可以改变多态的时候type的值
//class Son(override val name: String, val age: Int) : Father()

fun main() {
    val myModule = SerializersModule {
//        polymorphic(baseClass = Father::class) {
//            subclass(Son::class)
//        }
        //第一步，这里要声明Any为父类
        polymorphic(baseClass = Any::class) {
            subclass(Son::class)
            //当反序列化多态时，可能没有type，或者type的值不对，那么默认使用这个实现类反序列化
            defaultDeserializer {
                Son.serializer()
            }
        }
    }
    val json = Json { serializersModule = myModule }

//    val father: Father = Son("yu", 20)
    //这里使用Any是不行的
    val father: Any = Son("yu", 20)
//    println(json.encodeToString(father))
    //第二步，这里要声明Any为PolymorphicSerializer类型
    println(json.encodeToString(PolymorphicSerializer(Any::class), father))


    //多态的序列化，会有个type的字段序列化进去，表示序列化的实际类型
//    val father: Father = Son("yu", 22)
//    println(Json.encodeToString(father))

}


















