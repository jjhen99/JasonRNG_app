package com.example.jasonrng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
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

        var tables = arrayListOf<ArrayList<String>>()
        var numberOfKids:Int = 0
        var sizeNumber:Int = 0



        goButton.setOnClickListener {
            tables = arrayListOf<ArrayList<String>>()

            textView.text = "Kid "+ kidsText.text.toString() + " goes to table "+ tablesText.text.toString()
            //println(numberOfKids)

            numberOfKids = Integer.parseInt(kidsText.text.toString())
            var tableNumber = Integer.parseInt(tablesText.text.toString())
            sizeNumber = numberOfKids/tableNumber

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

            textView.text = "The table list looks like this: $tables"
            nextKid.isClickable = true
        }

        nextKid.setOnClickListener {

            var availableTables = tables.filter { it.size < sizeNumber }

            if (availableTables.isNotEmpty()){
                var randomTableIndex = Random.nextInt(0,availableTables.size)
                var assignedTable = availableTables[randomTableIndex]

                assignedTable.add("Kid $numberOfKids")

                textView.text = "Kid $numberOfKids goes to table ${tables.indexOf(assignedTable) + 1}"
                //textView.text = tables.toString()
                numberOfKids--
                kidNumberText.text = "Kids left: $numberOfKids"
            }

            else if(numberOfKids<=0){

                kidNumberText.text = "Kids left: 0"
                textView.text = "All tables are full: $tables"

            }

            else{

                var availableTablesForOthers = tables.filter { it.size < (sizeNumber+1) }

                if (availableTablesForOthers.isNotEmpty()){
                    var randomNumber = Random.nextInt(0,availableTablesForOthers.size)
                    var assignedTableForOthers = availableTablesForOthers[randomNumber]
                    assignedTableForOthers.add("Kid $numberOfKids")
                    textView.text = "Kid $numberOfKids goes to table ${tables.indexOf(assignedTableForOthers) + 1}"
                    numberOfKids--
                    kidNumberText.text = "Kids left: $numberOfKids"
                }

            }

        }

    }
}