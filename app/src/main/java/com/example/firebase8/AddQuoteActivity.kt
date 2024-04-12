package com.example.firebase8

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase8.utilities.QuotesDatabase.addTDB
import com.example.firebase8.utilities.ViewUtilities.bindView

class AddQuoteActivity : AppCompatActivity() {
    private val etQuote by bindView<EditText>(R.id.etQuote)
    private val etAuthor by bindView<EditText>(R.id.etAuthor)
    private val btAdd by bindView<Button>(R.id.btAdd)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_quote)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btAdd.setOnClickListener {
            addTDB(etQuote, etAuthor,it)
        }
    }
}