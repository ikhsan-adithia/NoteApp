package com.example.noteapp.core.util

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}