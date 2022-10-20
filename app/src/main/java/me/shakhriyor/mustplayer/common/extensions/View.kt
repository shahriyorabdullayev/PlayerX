package me.shakhriyor.mustplayer.common.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.lang.StringBuilder

fun View.visibility(isVisibility: Boolean){
    visibility = if (isVisibility){
        View.GONE } else { View.GONE }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.showSnackBar(message: String){
    Snackbar.make(this, message, 1500).show()
}

