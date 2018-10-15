package com.muellerwulff.viewmodelextensions.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.muellerwulff.viewmodelextensions.example.databinding.ActivityMainBinding
import com.muellerwulff.viewmodelextensions.observeRequired
import com.muellerwulff.viewmodelextensions.requireValue
import com.muellerwulff.viewmodelextensions.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model by lazy { viewModel { MainModel("this is a text: ", 8649) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.version = model.version

        model.number.observeRequired(this) {
            binding.number = it.toString()
        }
        model.numberMultitude.observeRequired(this) {
            binding.multitude = it.toString()
        }

        binding.setOnClickPrint { binding.text = model.print() }
    }

    override fun onResume() {
        super.onResume()
        binding.number = model.number.requireValue().toString()
    }
}
