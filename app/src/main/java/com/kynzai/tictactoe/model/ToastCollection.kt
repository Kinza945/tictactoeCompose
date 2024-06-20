package com.kynzai.tictactoe.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast

private val mToastHandler by lazy { Handler(Looper.getMainLooper()) }

private var mToast: Toast? = null

fun Any?.toast(length: Int = Toast.LENGTH_SHORT) {
    postToast(toString(), length)
}

private fun postToast(text: String, duration: Int) {
    mToastHandler.post {
        setToast(text, duration)
        mToast?.show()
    }
}

private fun setToast(text: String, duration: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        if (mToast == null) {
            mToast = Toast.makeText(appContext, text, duration)
        } else {
            mToast?.duration = duration
            mToast?.setText(text)
        }
    } else {
        if (mToast != null) {
            mToast?.cancel()
            mToast = null
        }
        mToast = Toast.makeText(appContext, text, duration)
    }
}

fun <T> Collection<T>.to2DArray(len: Int): List<List<T>> {
    val result = arrayListOf<ArrayList<T>>()

    var counter = 1
    var value = arrayListOf<T>()
    forEach { t ->
        if (counter == len) {
            counter = 1

            value.add(t)
            result.add(value)
            value = arrayListOf()
        } else {
            value.add(t)
            counter++
        }
    }

    return result
}

fun <T> Collection<Collection<T>>.to1DArray(): List<T> {
    val result = arrayListOf<T>()

    forEach { inner ->
        inner.forEach { t -> result.add(t) }
    }

    return result
}
