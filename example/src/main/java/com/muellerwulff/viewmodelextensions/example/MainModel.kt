package com.muellerwulff.viewmodelextensions.example

import android.arch.lifecycle.ViewModel
import com.muellerwulff.viewmodelextensions.asLiveData
import com.muellerwulff.viewmodelextensions.liveDataOf
import com.muellerwulff.viewmodelextensions.requireValue
import java.util.*

/**
 * @author Hans Markwart
 *
 * created at 01.05.2018.
 */
class MainModel(
	private val string: String,
	number: Int
) : ViewModel() {

	private val _number = liveDataOf(number)
	val number = _number.asLiveData()

	fun print(): String {
		_number.value = _number.requireValue() + 1
		return string + UUID.randomUUID().toString()
	}
}