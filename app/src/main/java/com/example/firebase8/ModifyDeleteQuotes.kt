package com.example.firebase8

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase8.components.QuotesAdapter
import com.example.firebase8.utilities.QuotesDatabase.retrieveAllQuotes
import com.example.firebase8.utilities.ViewUtilities.bindView
import kotlinx.coroutines.launch

class ModifyDeleteQuotes : AppCompatActivity() {
    private val recyclerQuotes by bindView<RecyclerView>(R.id.recyclerView)
    private lateinit var quotes: List<Map<String, String>>
    private lateinit var quotesAdapter: QuotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modify_delete_quotes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load data
        reloadQuotes(this)

    }

    private fun reloadQuotes(context: Context) {
        lifecycleScope.launch {
            quotes = retrieveAllQuotes()
            Log.d("ModifyDeleteQuotes", "Quotes retrieved: $quotes") // Log quotes after retrieval
            quotesAdapter = QuotesAdapter(quotes, context) { reloadQuotes(context) };
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        recyclerQuotes.layoutManager = LinearLayoutManager(this)
        recyclerQuotes.adapter = quotesAdapter
    }

}