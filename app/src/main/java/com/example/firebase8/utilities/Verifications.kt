package com.example.firebase8.utilities

import android.widget.EditText

object Verifications {
    fun verifyNotEmpty(vararg editTexts: EditText): Boolean {
        var allFieldsValid = true
        for (editText in editTexts) {
            if (editText.text.isEmpty()) {
                editText.error = "Campo no puede estar vacío"
                allFieldsValid = false
            }
        }
        return allFieldsValid
    }
}