package com.muellerwulff.viewmodelextensions.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.muellerwulff.viewmodelextensions.example.databinding.ActivityMainBinding
import com.muellerwulff.viewmodelextensions.observeEvent
import com.muellerwulff.viewmodelextensions.viewModelsCustom

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model by viewModelsCustom { MainModel("this is a text: ", 8649) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.version = model.version

        model.number.observe(this) {
            binding.number = it.toString()
        }
        model.numberMultitude.observe(this) {
            binding.multitude = it.toString()
        }

        model.warning.observeEvent(this) { warning ->
            Toast.makeText(this, warning, Toast.LENGTH_SHORT).show()
        }

        binding.setOnClickPrint { binding.text = model.print() }
    }

    override fun onResume() {
        super.onResume()
        binding.number = model.number.value!!.toString()
    }
}
