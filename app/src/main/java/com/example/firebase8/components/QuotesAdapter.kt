package com.example.firebase8.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase8.R

class QuotesAdapter(
    private val quotes: List<Map<String, String>>,
    private val context: Context,
    private val reloadQuotes: () -> Unit
) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quoteTextView: TextView = itemView.findViewById(R.id.quoteTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quoteMap = quotes[position]
        holder.quoteTextView.text = quoteMap["quote"]
        holder.authorTextView.text = quoteMap["author"]

        // Set onClickListener for btnEditQuote
        holder.itemView.findViewById<View>(R.id.btnEditQuote).setOnClickListener {
            // Call a function to open the modal here
            QuotesModal(context).openEditModal(quoteMap, reloadQuotes)
        }
        holder.itemView.findViewById<View>(R.id.btnDeleteQuote).setOnClickListener {
            QuotesModal(context).openDeleteModal(quoteMap, reloadQuotes)
        }
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}
