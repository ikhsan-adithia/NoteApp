package com.example.noteapp.core.util

import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.noteapp.R

@BindingAdapter("android:visibility")
fun setVisibility(view: View, state: Boolean) {
    view.visibility = if (state) View.VISIBLE else View.GONE
}

@BindingAdapter("android:noteColor")
fun radioGroupNoteColor(radioGroup: RadioGroup, noteColor: Int) {
    when (noteColor) {
        R.color.rb_blue -> {
            radioGroup.check(R.id.rb_blue)
        }
        R.color.rb_purple -> {
            radioGroup.check(R.id.rb_purple)
        }
        R.color.rb_green -> {
            radioGroup.check(R.id.rb_green)
        }
        R.color.rb_magenta -> {
            radioGroup.check(R.id.rb_magenta)
        }
        else -> {
            radioGroup.check(R.id.rb_purple)
        }
    }
}