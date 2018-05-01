package com.sihamark.viewmodelextensions.example

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sihamark.viewmodelextensions.example.databinding.ActivityMainBinding
import com.sihamark.viewmodelextensions.observeRequired
import com.sihamark.viewmodelextensions.requireValue
import com.sihamark.viewmodelextensions.viewModel

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var model: MainModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		model = viewModel { MainModel("this is a text: ", 8649) }

		model.number.observeRequired(this, {
			binding.number = it.toString()
		})

		binding.setOnClickPrint { binding.text = model.print() }
	}

	override fun onResume() {
		super.onResume()
		binding.number = model.number.requireValue().toString()
	}
}
