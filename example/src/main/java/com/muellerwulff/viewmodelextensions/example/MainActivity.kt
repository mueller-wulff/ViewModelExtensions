package com.muellerwulff.viewmodelextensions.example

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.muellerwulff.viewmodelextensions.example.databinding.ActivityMainBinding
import com.muellerwulff.viewmodelextensions.observeRequired
import com.muellerwulff.viewmodelextensions.requireValue
import com.muellerwulff.viewmodelextensions.viewModel

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var model: MainModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		model = viewModel {
			MainModel(
				"this is a text: ",
				8649
			)
		}

		model.number.observeRequired(this) {
			binding.number = it.toString()
		}

		binding.setOnClickPrint { binding.text = model.print() }
	}

	override fun onResume() {
		super.onResume()
		binding.number = model.number.requireValue().toString()
	}
}
