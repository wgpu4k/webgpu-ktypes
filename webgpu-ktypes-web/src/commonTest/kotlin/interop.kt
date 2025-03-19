package io.ygdrasil.webgpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InteropTest {
    @Test
    fun testCreateJsObject() {
        val obj = createJsObject<JsObject>()
        assertNotNull(obj)
    }

    @Test
    fun testJsArray() {
        val obj1 = createJsObject<JsObject>()
        val obj2 = createJsObject<JsObject>()
        val array = jsArray(obj1, obj2)
        assertNotNull(array)
    }

    @Test
    fun testMapJsArray() {
        val list = listOf("a", "b", "c")
        val jsArray = list.mapJsArray<String, JsObject> { it.asJsString().castAs() }
        assertNotNull(jsArray)
    }

    @Test
    fun testNumberConversions() {
        val jsNumber = 42.asJsNumber()
        assertEquals(42, jsNumber.asInt())
        assertEquals(42.0, jsNumber.asDouble())
        assertEquals(42f, jsNumber.asFloat())
        assertEquals(42L, jsNumber.asLong())
        assertEquals(42.toShort(), jsNumber.asShort())
        assertTrue(jsNumber.asBoolean())

        val jsZero = 0.asJsNumber()
        assertEquals(false, jsZero.asBoolean())
    }

    @Test
    fun testBooleanConversions() {
        val jsTrue = true.asJsNumber()
        val jsFalse = false.asJsNumber()

        assertTrue(jsTrue.asBoolean())
        assertEquals(false, jsFalse.asBoolean())
    }

    @Test
    fun testStringConversions() {
        val jsString = "hello".asJsString()
        val jsObject = jsString.castAs()
        assertNotNull(jsObject)
    }

    @Test
    fun testJsMap() {
        val map = jsMap<JsObject, JsObject>()
        assertNotNull(map)

        val key1 = createJsObject<JsObject>()
        val value1 = "value1".asJsString().castAs()
        val key2 = createJsObject<JsObject>()
        val value2 = "value2".asJsString().castAs()

        val kotlinMap = mapOf(key1 to value1, key2 to value2)
        val jsMap = kotlinMap.toJsMap()
        assertNotNull(jsMap)
    }
}
