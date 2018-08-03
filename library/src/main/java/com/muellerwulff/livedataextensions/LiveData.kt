package com.muellerwulff.livedataextensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

fun <T> liveDataOf(default: T? = null): LiveData<T> = MutableLiveData<T>().also {
    it.value = default
}

fun <T> mutableLiveDataOf(default: T? = null) = MutableLiveData<T>().also {
    it.value = default
}
