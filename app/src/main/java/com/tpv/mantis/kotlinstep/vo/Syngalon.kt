package com.tpv.mantis.kotlinstep.vo

import android.util.Log

class Syngalon constructor(name: String){

    val TAG: String = "Syngalon"
    var url: String = "www.syngalon.com"
    var country: String = "CN"
    var siteName = name

    init {
        Log.d(TAG, "初始化网络名：$name")
    }

    constructor(name: String, alexa: Int) : this(name) {
        Log.d(TAG, "Alexa 排名 $alexa")
    }

    fun printTest() {
        Log.d(TAG, "我是类的函数")
    }
}