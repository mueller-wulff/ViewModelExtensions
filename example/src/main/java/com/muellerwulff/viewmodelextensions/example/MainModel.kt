package com.muellerwulff.viewmodelextensions.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.muellerwulff.viewmodelextensions.Event
import com.muellerwulff.viewmodelextensions.asEvent
import com.muellerwulff.viewmodelextensions.asLiveData
import com.muellerwulff.viewmodelextensions.mutableLiveDataOf
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

    private var numberOfGenerations = 0

    private val _warning = mutableLiveDataOf<Event<String>?>()
    val warning = _warning.asLiveData()

    val version = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

    private val _number = mutableLiveDataOf(initialNumber)
    val number = _number.asLiveData()

    val numberMultitude = number.map { it * 25 }

    fun print(): String {
        numberOfGenerations++
        if (numberOfGenerations > 5) {
            numberOfGenerations = 0
            _warning.value = "did some generations".asEvent()
        }
        _number.value = _number.value!! + 1
        return string + UUID.randomUUID().toString()
    }
}