package com.example.noteapp.core.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, state: Boolean) {
    view.visibility = if (state) View.VISIBLE else View.GONE
}