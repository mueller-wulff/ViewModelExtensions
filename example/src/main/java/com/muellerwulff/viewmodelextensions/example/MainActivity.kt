package com.muellerwulff.viewmodelextensions.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.muellerwulff.viewmodelextensions.observeEvent
import com.muellerwulff.viewmodelextensions.viewModelsCustom
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val model by viewModelsCustom { MainModel("this is a text: ", 8649) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        version.text = model.version

        model.number.observe(this) {
            number.text = it.toString()
        }
        model.numberMultitude.observe(this) {
            multitude.text = it.toString()
        }

        model.warning.observeEvent(this) { warning ->
            Toast.makeText(this, warning, Toast.LENGTH_SHORT).show()
        }

        action_print.setOnClickListener { text.text = model.print() }
    }

    override fun onResume() {
        super.onResume()
        number.text = model.number.value!!.toString()
    }
}
