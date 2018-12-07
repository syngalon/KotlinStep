package com.tpv.mantis.kotlinstep.vo

import android.util.Log

class Teacher {
    var lastName: String = "Zhang"
        get() = field.toUpperCase()

    var no: Int = 100
        set(value) {
            field = when (value < 10) {
                true -> value
                false -> -1
            }

        }

    var height: Float = 172.5f
        private set

    lateinit var gender: String
    val weight: Float by lazy {
        Log.d("mantis", "init")
        56.4f
    }
}