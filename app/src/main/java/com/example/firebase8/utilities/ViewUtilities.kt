package com.example.firebase8.utilities

import android.view.View
import androidx.appcompat.app.AppCompatActivity

object ViewUtilities {
    fun <T : View> AppCompatActivity.bindView(id: Int): Lazy<T> {
        return lazy { findViewById<T>(id) }
    }
}
