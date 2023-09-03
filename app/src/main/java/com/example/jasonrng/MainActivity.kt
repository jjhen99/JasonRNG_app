package com.example.jasonrng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
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

        var tables = arrayListOf<ArrayList<String>>()
        var numberOfKids:Int = 0
        var sizeNumber:Int = 0

        textView.text = "Enter the kids and tables\nPress start to begin"

        fun startRNG() {
            tables = arrayListOf<ArrayList<String>>()

            textView.text = "Go to table\n"+ tablesText.text.toString()
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


        goButton.setOnClickListener {
            startRNG()
        }

        nextKid.setOnClickListener {
            sendNextKid()
        }

        seeTables.setOnClickListener {
            goToListView()
        }



    }


}