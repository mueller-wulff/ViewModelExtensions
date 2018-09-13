package com.muellerwulff.viewmodelextensions.example

import android.arch.lifecycle.ViewModel
import com.muellerwulff.viewmodelextensions.asLiveData
import com.muellerwulff.viewmodelextensions.liveDataOf
import com.muellerwulff.viewmodelextensions.map
import com.muellerwulff.viewmodelextensions.requireValue
import java.util.*

/**
 * @author Hans Markwart
 *
 * created at 01.05.2018.
 */
class MainModel(
    private val string: String,
    initialNumber: Int
) : ViewModel() {

    val version = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

    private val _number = liveDataOf(initialNumber)
    val number = _number.asLiveData()

    val numberMultitude = number.map { it * 25 }

    fun print(): String {
        _number.value = _number.requireValue() + 1
        return string + UUID.randomUUID().toString()
    }
}