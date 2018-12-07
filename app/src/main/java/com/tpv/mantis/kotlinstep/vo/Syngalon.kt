package com.tpv.mantis.kotlinstep.vo

import android.util.Log
import com.tpv.mantis.kotlinstep.MainActivity

class Syngalon constructor(name: String){

    var url: String = "www.syngalon.com"
    var country: String = "CN"
    var siteName = name

    init {
        Log.d(MainActivity.TAG, "初始化网络名：$name")
    }

    constructor(name: String, alexa: Int) : this(name) {
        Log.d(MainActivity.TAG, "Alexa 排名 $alexa")
    }

    fun printTest() {
        Log.d(MainActivity.TAG, "我是类的函数")
    }
}