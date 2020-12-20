package com.jkhome.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var lastNumeric : Boolean = false
    var lastDot: Boolean = false
    val stack: MutableList<String> = mutableListOf()

    lateinit var tvinput : TextView
    lateinit var tvhistory : TextView

    enum class State{
        WaitNumber,
        WaitOperator
    }

    val state:State = State.WaitNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvinput = findViewById<TextView>(R.id.tvInput)
        tvhistory = findViewById<TextView>(R.id.tvHistory)
    }



    fun onDigit(view: View){
        var value = (view as Button).text

        tvinput.append(value);
    }


    fun onDecimalPoint(view: View)
    {
        if(lastNumeric && !lastDot)
        {
            tvinput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onClear(view: View)
    {

        tvinput.text = ""
        lastNumeric = false
        lastDot = false
        stack.clear()
    }

    fun onEqual(view: View)
    {
        if(stack.count() < 2)
            return
        if(tvinput.length() == 0)
            return


        stack.add(tvinput.text.toString())


        var result:Double = 0.0
       while(stack.count() != 1)
       {
           var lhs = stack[0].toDouble()
           var operator = stack[1]
           var rhs = stack[2].toDouble()

            Log.v("test", operator)
           when (operator) {
               "-" -> result = lhs - rhs
               "+" -> result = lhs + rhs
               "*" -> result = lhs * rhs
               "/" -> result = lhs / rhs
           }
           Log.v("test", result.toString())

           stack.removeAt( 0)
           stack.removeAt( 0)
           stack[0] = result.toString()
       }

        tvinput.text = stack[0].toString()
        tvhistory.text = result.toString()

    }

    fun onOperator(view: View){


        val value = (view as Button).text

        if(value == "-" && tvinput.length() == 0)
        {
            tvinput.append((view as Button).text);
            return
        }

        if(stack.count() >= 2 && isOperator(stack[stack.count() - 1]))
        {
            return
        }

        stack.add( tvinput.text.toString())
        stack.add(value.toString())
        tvhistory.append(tvinput.text.toString() + value)
        lastDot = false
        tvinput.text = ""

    }


    private fun isOperator(value: String) : Boolean{
       return value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }
        else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }

}