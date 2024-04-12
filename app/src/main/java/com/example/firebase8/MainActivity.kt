package com.example.firebase8

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase8.utilities.ViewUtilities.bindView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val addButton by bindView<FloatingActionButton>(R.id.btnAdd)
    private val editButton by bindView<FloatingActionButton>(R.id.btnEdit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Firebase.database
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddQuoteActivity::class.java)
            startActivity(intent)
        }
        editButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ModifyDeleteQuotes::class.java)
            startActivity(intent)
        }
    }
}