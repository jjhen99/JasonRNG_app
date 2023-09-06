package com.example.jasonrng

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tablesText = findViewById<EditText>(R.id.tablesText)
        var kidsText = findViewById<EditText>(R.id.kidsText)
        var goButton = findViewById<Button>(R.id.goButton)
        var textView = findViewById<TextView>(R.id.textView3)
        var nextKid = findViewById<Button>(R.id.nextKid)
        var kidNumberText = findViewById<TextView>(R.id.textView4)
        var seeTables = findViewById<Button>(R.id.seeTables)
        var layout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        var kidHeadline = findViewById<TextView>(R.id.kidNumberHeadLineTextView)
        var tablesHeadline = findViewById<TextView>(R.id.tablesNumberHeadLineTextView)

        var tables = arrayListOf<ArrayList<String>>()
        var numberOfKids:Int = 0
        var sizeNumber:Int = 0
        var clicked = 0

        layout.setBackgroundColor(Color.parseColor("#121212"))
        tablesHeadline.setTextColor(Color.parseColor("#F8F8F8"))
        kidHeadline.setTextColor(Color.parseColor("#F8F8F8"))
        kidsText.setTextColor(Color.parseColor("#F8F8F8"))
        tablesText.setTextColor(Color.parseColor("#F8F8F8"))
        kidNumberText.setTextColor(Color.parseColor("#F8F8F8"))

        textView.text = "Enter the kids and tables\nPress start to begin"

        fun startRNG() {
            tables = arrayListOf<ArrayList<String>>()

            textView.text = "Go to table\n"+ tablesText.text.toString()
            //println(numberOfKids)

            numberOfKids = kidsText.text.toString().toIntOrNull() ?: 0
            var tableNumber = tablesText.text.toString().toIntOrNull() ?: 0

            sizeNumber = if (tableNumber != 0) {
                numberOfKids / tableNumber
            } else {
                0
            }

            textView.text = "The size of kids per table is: $sizeNumber"
            kidNumberText.text = "Kids left: $numberOfKids"

            while (tableNumber > 0){
                var table = ArrayList<String>()
                tables.add(table)
                tableNumber--
            }

            val totalNumberOfKids:Int = numberOfKids

            var firstLoopLimit:Int = totalNumberOfKids - tables.size * sizeNumber

            if(firstLoopLimit < 0){
                firstLoopLimit = 0
            }

            textView.text = "Click Next Kid to proceed"
            nextKid.isClickable = true
        }


        fun sendNextKid(){
            var availableTables = tables.filter { it.size < sizeNumber }

            if (availableTables.isNotEmpty()){
                var randomTableIndex = Random.nextInt(0,availableTables.size)
                var assignedTable = availableTables[randomTableIndex]

                assignedTable.add("Kid $numberOfKids")

                textView.text = "Kid $numberOfKids\n Go to table\n ${tables.indexOf(assignedTable) + 1}"
                //textView.text = tables.toString()
                numberOfKids--
                kidNumberText.text = "Kids left: $numberOfKids"
            }

            else if(numberOfKids<=0){

                kidNumberText.text = "Kids left: 0"
                textView.text = "All tables are full!\nPress start to start again."
                nextKid.isClickable = false

                //sendTablesToListView()
            }

            else{

                var availableTablesForOthers = tables.filter { it.size < (sizeNumber+1) }

                if (availableTablesForOthers.isNotEmpty()){
                    var randomNumber = Random.nextInt(0,availableTablesForOthers.size)
                    var assignedTableForOthers = availableTablesForOthers[randomNumber]
                    assignedTableForOthers.add("Kid $numberOfKids")
                    textView.text = "Kid $numberOfKids\nGo to table\n ${tables.indexOf(assignedTableForOthers) + 1}"
                    numberOfKids--
                    kidNumberText.text = "Kids left: $numberOfKids"
                }

            }
        }

        fun sendTablesToListView(){
            var gson = Gson()
            var dataListJson = gson.toJson(tables)

            var intent = Intent(this, ListView::class.java)
            intent.putExtra("tables", dataListJson)
        }

        fun goToListView(){
            var intent2 = Intent(this, ListView::class.java)
            startActivity(intent2)
        }

        fun changeBackground(){
            clicked+=1
            if (clicked>2){
                clicked = 1
            }
            when(clicked){
                1 -> {
                    textView.setTextColor(Color.parseColor("#F8F8F8"))

                }

                2 -> {
                    textView.setTextColor(Color.parseColor("#6495ED"))
                }
            }
        }


        goButton.setOnClickListener {
            startRNG()

        }

        nextKid.setOnClickListener {
            sendNextKid()
            changeBackground()
        }

        seeTables.setOnClickListener {
            goToListView()
        }



    }


}