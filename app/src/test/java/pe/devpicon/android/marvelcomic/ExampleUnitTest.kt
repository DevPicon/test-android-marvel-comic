package pe.devpicon.android.marvelcomic

import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    //@Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
        val list = listOf(1,2,3,4,5)
        val squared = list.map{ i -> i * i}
        assertTrue(squared.containsAll(listOf(1,4,9,16,25)))
        squared.forEach { println(it) }
    }

    @Test
    fun lookingForAnObject(){

        val people = listOf<Person>(Person("Adrian", 30),Person("Erik", 26),Person("Armando", 35))
        val person = people.find { it.name == "Armando" }
        println(person)
    }
}

data class Person(val name: String, var age: Int)