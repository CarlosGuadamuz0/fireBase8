package com.example.firebase8.components

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.firebase8.R
import com.example.firebase8.utilities.QuotesDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog

class QuotesModal(private val context: Context) {

    fun openEditModal(quoteMap: Map<String, String>, reloadQuotes: () -> Unit) {
        // Inflate the dialog layout
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_quote, null)

        // Find views within the inflated layout
        val editQuoteTextView = dialogView.findViewById<EditText>(R.id.editQuoteTextView)
        val editAuthorTextView = dialogView.findViewById<EditText>(R.id.editAuthorTextView)
        val btnSave = dialogView.findViewById<Button>(R.id.btnSave)

        // Populate the dialog fields with the quote data
        editQuoteTextView.setText(quoteMap["quote"])
        editAuthorTextView.setText(quoteMap["author"])

        val dialog = BottomSheetDialog(context)
        dialog.setContentView(dialogView)

        btnSave.setOnClickListener {
            // Get the edited quote and author
            val editedQuote = editQuoteTextView.text.toString()
            val editedAuthor = editAuthorTextView.text.toString()
            // Create a map containing the edited quote data
            val editedQuoteMap = mapOf(
                "quote" to editedQuote,
                "author" to editedAuthor
            )
            // Call the function to edit the quote in the database
            QuotesDatabase.editValueInDatabase(context, quoteMap["key"]!!, editedQuoteMap)
            reloadQuotes()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun openDeleteModal(quoteMap: Map<String, String>, reloadQuotes: () -> Unit) {
        // Inflate the dialog layout
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_delete_quote, null)

        // Find views within the inflated layout
        dialogView.findViewById<TextView>(R.id.deleteMessageTextView)
        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(dialogView)


        btnYes.setOnClickListener {
            // Call the function to delete the quote from the database
            QuotesDatabase.deleteValueFromDatabase(context, quoteMap["key"]!!)
            reloadQuotes()
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}