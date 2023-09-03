package com.example.jasonrng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)


        var listView = findViewById<ListView>(R.id.listView)


        var dataListJson = intent.getStringExtra("tables")
        var gson = Gson()
        var tables: ArrayList<ArrayList<String>> = gson.fromJson(dataListJson, object : TypeToken<ArrayList<ArrayList<String>>>() {}.type)


        //var tables:ArrayList<ArrayList<String>> = intent.getSerializableExtra("tables") as ArrayList<ArrayList<String>>

        var arrayAdapter = ArrayAdapter(this,R.layout.activity_list_view,R.id.textViewList,tables)

        listView.adapter = arrayAdapter
    }
}