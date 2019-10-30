package com.astek.asteksupport.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

class ShowUtil {

    companion object {

        fun showMessage(view:View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}