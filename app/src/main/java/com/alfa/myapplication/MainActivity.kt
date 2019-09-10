package com.alfa.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val preferPrice: SharedPreferences by lazy { getSharedPreferences("price", Context.MODE_PRIVATE) }
    private val preferAuto: SharedPreferences by lazy { getSharedPreferences("auto", Context.MODE_PRIVATE) }
    private val preferDist: SharedPreferences by lazy { getSharedPreferences("dist", Context.MODE_PRIVATE) }
    private val preferResult: SharedPreferences by lazy { getSharedPreferences("result", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result.text =  preferResult.getString("result","0.0R$")
        editPrice.setText(preferPrice.getString("price",""))
        editAutonomia.setText(preferAuto.getString("auto", ""))
        editDistance.setText(preferDist.getString("dist", ""))

        btnCalc.setOnClickListener {
            val final = "%.2f".format(calcular())+"R$"
            val edResult = preferResult.edit()
            edResult.putString("result", final).commit()
            result.text = final
        }
    }

    private fun calcular():Float{
        var preco = editPrice.text.toString()
        var distancia = editDistance.text.toString()
        var autonomia = editAutonomia.text.toString()

        val editp = preferPrice.edit()
        editp.putString("price", preco).commit()
        val edita = preferAuto.edit()
        edita.putString("auto", autonomia).commit()
        val editd = preferDist.edit()
        editd.putString("dist",distancia).commit()

        when{
            (editPrice.text == null || editPrice.text.toString() == "" )->{
                Toast.makeText(this,"Informe todos valores",Toast.LENGTH_SHORT).show()
                return 0f
            }
            (editAutonomia.text == null || editAutonomia.text.toString() == "" || editAutonomia.text.toString().toInt() == 0 )->{
                Toast.makeText(this,"Informe todos valores",Toast.LENGTH_SHORT).show()
                return 0f
            }
            (editDistance.text == null || editDistance.text.toString() == "" )->{
                Toast.makeText(this,"Informe todos valores",Toast.LENGTH_SHORT).show()
                return 0f
            }
            else->{
                return (preco.toFloat()*distancia.toFloat())/autonomia.toFloat()
            }
        }
    }

    //btnCalc.setOnClickListener(View.OnClickListener {  })
    //fun calcular(v: View){}
}
