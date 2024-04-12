package com.example.firebase8.utilities

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.firebase8.utilities.Verifications.verifyNotEmpty
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object QuotesDatabase {
    fun addTDB(quote: EditText, author: EditText, view: View) {
        if (verifyNotEmpty(quote, author)) {
            val key = generateKey("quotes")
            key?.let {
                setValueInDatabase(
                    view.context,
                    key,
                    createQuoteMap(quote.text.toString(), author.text.toString(), it)
                )
            }
        }
    }

    private fun generateKey(reference: String): String? {
        return FirebaseDatabase.getInstance().getReference(reference).push().key
    }

    private fun createQuoteMap(quote: String, author: String, key: String): Map<String, String> {
        return mapOf(
            "quote" to quote,
            "author" to author,
            "key" to key
        )
    }

    private fun setValueInDatabase(context: Context, key: String, quoteMap: Map<String, String>) {
        FirebaseDatabase.getInstance().getReference("quotes").child(key)
            .setValue(quoteMap)
            .addOnCompleteListener { task ->
                showToast(
                    context,
                    if (task.isSuccessful) "Agregado!" else "Error al agregar: ${task.exception}"
                )
            }
    }

    fun editValueInDatabase(context: Context, key: String, quoteMap: Map<String, String>) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("quotes").child(key)
        databaseReference.updateChildren(quoteMap)
            .addOnCompleteListener { task ->
                showToast(
                    context,
                    if (task.isSuccessful) "Editado!" else "Error al editar: ${task.exception}"
                )
            }
    }

    fun deleteValueFromDatabase(context: Context, key: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("quotes").child(key)
        databaseReference.removeValue()
            .addOnCompleteListener { task ->
                showToast(
                    context,
                    if (task.isSuccessful) "Deleted!" else "Error al eliminar: ${task.exception}"
                )
            }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    suspend fun retrieveAllQuotes(): List<Map<String, String>> = suspendCoroutine { continuation ->
        FirebaseDatabase.getInstance().getReference("quotes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val quotesList = dataSnapshot.children.map { snapshot ->
                        val quoteKey = snapshot.key.orEmpty()
                        val quoteObject = snapshot.value as? Map<String, String> ?: emptyMap()
                        val quote = quoteObject["quote"] ?: ""
                        val author = quoteObject["author"] ?: ""
                        mapOf("key" to quoteKey, "quote" to quote, "author" to author)
                    }
                    continuation.resume(quotesList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Database error: ${databaseError.message}")
                    continuation.resumeWithException(databaseError.toException())
                }
            })
    }
}