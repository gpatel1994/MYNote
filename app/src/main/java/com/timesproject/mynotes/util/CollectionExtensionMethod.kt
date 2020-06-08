package com.timesproject.mynotes.util

fun <T> List<T>?.listSize() : Int {
    return this?.size ?: 0
}