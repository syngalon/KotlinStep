package com.tpv.mantis.kotlinstep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.tpv.mantis.kotlinstep.vo.Teacher
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KProperty


class MainActivity : AppCompatActivity(), View.OnClickListener {


    val TAG = "KotlinStep"

    data class Person(val name: String, val age: Int)

    val peopleList = listOf(Person("zhangsan", 12), Person("lisi", 20), Person("wangwu", 8))

    fun findTheOldest(people: List<Person>): Person {
        var maxAge = 0
        var theOldestPerson: Person? = null
        for (person in people) {
            if (person.age > maxAge) {
                maxAge = person.age
                theOldestPerson = person
            }
        }
        return theOldestPerson!!
    }


    val sum = { x: Int, y: Int -> x + y }


    infix fun Int.add(x: Int): Int = this + x


    fun sum2(x: Int = 0, y: Int = 0): Int {
        return x + y
    }

    fun operate(x: Int = 0, y: Int = 0, body: (Int, Int) -> Int) {
        result1View.text = body(x, y).toString()
    }

    fun getValue() {
        operate(3, 7, ::sum2)
    }

    fun getValue2() {
        operate(3, 7) { x, y ->
            x * y
        }
    }

    fun foo(arg1: String = "syngalon", arg2: Boolean, arg3: Int): String {
        return "arg1: $arg1, arg2: $arg2, arg3: $arg3"
    }

    fun upCase(str: String, body: (String) -> String) {
        result1View.text = body(str)
    }

    fun transform(str: String) {
        upCase(str) { it.toUpperCase() }
    }

    fun String.upper(body: (String) -> String) {
        result2View.text = body(this)
    }

    fun transform2(str: String) {
        str.upper { it.toUpperCase() }
    }

    val sum3 = fun Int.(other: Int): Int = this + other

    tailrec fun factRec(n: Int, total: Int = 1): Int =
            when (n) {
                1 -> total
                else -> factRec(n - 1, total * n)
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun fn(n: Int): Int {
        when (n) {
            0 -> return 1
            1 -> return 4
            else -> return 2 * fn(n - 1) + fn(n - 2)
        }
    }

    private fun <T> Collection<T>.joinToString(
            separator: String = ", ",
            prefix: String = "",
            postfix: String = "",
            transform: (T) -> String = { it.toString() }
    ): String {
        val result = StringBuffer(prefix)
        for ((index, element) in this.withIndex()) {
            Log.d("kkkk", "index: $index, element: $element")
            if (index > 0) {

                result.append(separator)
            }
            result.append(transform(element))
        }

        result.append(postfix)
        return result.toString()
    }

    enum class Delivery {STANDARD, EXPIRED}

    class Order(val itemCount: Int)

    fun getShippingCalc(delivery: Delivery): (Order) -> Double {
        if (delivery == Delivery.EXPIRED) {
            return {order -> 6 + 2.1 * order.itemCount }
        }
        return {order -> 1.2 * order.itemCount }
    }


    val getAge = { p: Person -> p.age }
    ///////////////////////////////////////////////////////////////////////
    data class Student(val name: String, val number: String?)
    class StudInfoFilter {
        var prefix: String = ""
        var onlyWithPhoneNumber: Boolean = false
        fun getPredicate(): (Student) -> Boolean {
            val startWithPrefix = {
                s: Student -> s.name.startsWith(prefix)
            }

            if (!onlyWithPhoneNumber) {
                return startWithPrefix
            }

            return {startWithPrefix(it) && it.number != null}
        }
    }

    val studInfoList = listOf(Student("Syngalon", "123-4567"),
            Student("LiLei", null),
            Student("Henry", "999-3333"),
            Student("Lucy", "8888888"))
    /////////////////////////////////////////////////////////////////////

    data class SiteVisit (
        val path: String,
        val duration: Double,
        val os: OS
    )

    enum class OS {WINDOWS, LINUX, MAC, IOS, ANDROID}
    val log2 = listOf(SiteVisit( "/", 34.0, OS.WINDOWS),
            SiteVisit("/", 22.0, OS.MAC),
            SiteVisit("/login", 12.0, OS.WINDOWS),
            SiteVisit("/signup", 8.0, OS.IOS),
            SiteVisit("/", 16.3, OS.ANDROID))

    private fun List<SiteVisit>.averageDuration(predicate: (SiteVisit) -> Boolean) = (filter(predicate).map(SiteVisit::duration)).average()
    ////////////////////////////////////////////////////////////////////

    fun decimalDigitValue(c: Char): Int {
        if (c !in '0'..'9') {
            throw IllegalArgumentException("Out of range")
        }
        Log.d(TAG, "c: ${c.toInt()}")
        return c.toInt() - '0'.toInt()  // convert to digit explicitly
    }

    val testLazy: String by lazy {
        Log.d(TAG, "lazyTest entered")
        "lazyTest"
    }

    private fun testLazyAttr() {
        Log.d(TAG, "Begin to init lazy attr")
        result1View.text = testLazy
    }
    private fun testTeacher():Unit {
        var teacher: Teacher = Teacher()
        teacher.lastName = "nang"
        result1View.text = String.format("lastName: %s, weight: %f", teacher.lastName, teacher.weight)

        teacher.no = 4

        teacher.gender = "male"
        result2View.text = String.format("no: %d, gender: %s", teacher.no, teacher.gender)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

            R.id.calcBtn -> {
                testLazyAttr()
            }

//            R.id.calcBtn -> {
//                var sum =  0
//                val content = "12345"
//                for (c in content) {
//                    Log.d(TAG, "char: $c")
//                    sum += decimalDigitValue(c)
//                }
//                result1View.text = sum.toString()
//
//                loop@ for (i in 1..10) {
//                    for (j in 1..10) {
//                        if (i * j == 100) {
//                            continue@loop
//                        }
//                    }
//                }
//            }
//            R.id.calcBtn -> {
////                for (siteVisit in log2) {
////                    if (siteVisit.os in setOf(OS.ANDROID,   OS.WINDOWS)) {
////                        Log.d(TAG, "siteVisit.name: ${siteVisit.duration}, siteVisit.os: ${siteVisit.os}")
////                    }
////                }
//                val avgDur = log2.averageDuration { it.os in setOf(OS.ANDROID,   OS.WINDOWS) }
//                Log.d(TAG, "log2 averageDuration: $avgDur")
//            }
//            R.id.calcBtn -> {
//                val studInfoFilter = StudInfoFilter()
//                studInfoFilter.prefix = "L"
//                studInfoFilter.onlyWithPhoneNumber = true
//                Log.d(TAG, "students' info: ${studInfoList.filter(studInfoFilter.getPredicate())}")
//            }
//            R.id.calcBtn -> result1View.text = findTheOldest(peopleList).toString()
//            R.id.calcBtn -> result1View.text = peopleList.minBy{ it.age }.toString()
//            R.id.calcBtn -> result1View.text = peopleList.minBy{ p: Person -> p.age }.toString()
//            R.id.calcBtn -> result1View.text = peopleList.minBy{ p -> p.age }.toString()
//            R.id.calcBtn -> result1View.text = peopleList.joinToString (separator = " ", transform = {p -> p.name})
//            R.id.calcBtn -> result1View.text = peopleList.joinToString (separator = ", "){p -> p.name}
//            R.id.calcBtn -> {
//                result1View.text = peopleList.maxBy(getAge).toString()
//                result2View.text = peopleList.minBy(Person::age).toString()
//            }
//            R.id.calcBtn -> result1View.text = sum(Integer.valueOf(inputEditor1.text.toString()), Integer.valueOf(inputEditor2.text.toString())).toString()
//            R.id.calcBtn -> result1View.text = (Integer.valueOf(inputEditor1.text.toString()) add Integer.valueOf(inputEditor2.text.toString())).toString()
//            R.id.calcBtn -> result1View.text = foo("hello", false, 89)
//            R.id.calcBtn -> {
//                transform(inputEditor1.text.toString())
//                transform2(inputEditor2.text.toString())
//            }
//            R.id.calcBtn -> result1View.text = factRec(5).toString()
//            R.id.calcBtn -> {
//                val letters = listOf("Syngalon", "Beta")
//                result1View.text = letters.joinToString()
//                result2View.text = letters.joinToString { it.toLowerCase() }
//                result2View.text = letters.joinToString(separator = " ", prefix = ", ", postfix = "!", transform = { it.toUpperCase() })
//            }
//            R.id.calcBtn -> {
//                val shipCalc1 = getShippingCalc(Delivery.EXPIRED)
//                val shipCalc2 = getShippingCalc(Delivery.STANDARD)
//                result1View.text = shipCalc1(Order(3)).toString()
//                result2View.text = shipCalc2(Order(3)).toString()
//            }

        }
    }

    override fun onResume() {
        super.onResume()
        calcBtn.setOnClickListener(this)
//        printMsg("kk")
//        val b = BaseImpl(10)
//        Derived(b).print()
        val e = Example()
        printMsg(e.p)
        e.p = "Syngalon"
        printMsg(e.p)
    }

    val printMsg = { str: String -> Log.d(TAG, str) }

    interface Base {
        fun print()
    }

    class BaseImpl(private val x: Int) : Base {
        override fun print() {
            printStr(x)
        }

        val printStr = { str: Int -> Log.d("TTTT", "$str") }
    }

    class Derived(b: Base) : Base by b {

    }
}

class Example {
    var p: String by Delegate()

}

class Delegate {
    val printMsg = { str: String -> Log.d("KKK", str) }
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, here delegates ${property.name}'s property"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        printMsg("$thisRef's ${property.name} has set value to $value")
    }
}




