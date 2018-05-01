package com.sihamark.viewmodelextensions.example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sihamark.viewmodelextensions.asLiveData
import com.sihamark.viewmodelextensions.requireValue
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

	private val _number = MutableLiveData<Int>().apply { value = number }
	val number = _number.asLiveData()

	fun print(): String {
		_number.value = _number.requireValue() + 1
		return string + UUID.randomUUID().toString()
	}
}