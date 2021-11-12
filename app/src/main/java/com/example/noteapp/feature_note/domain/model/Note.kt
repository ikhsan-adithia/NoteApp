package com.example.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.R
import java.lang.Exception

@Entity
data class Note(
    @PrimaryKey
    val id: Int? = null,
    var title: String,
    var content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(
            R.color.rb_blue,
            R.color.rb_purple,
            R.color.rb_green,
            R.color.rb_magenta
        )
    }
}

class InvalidNoteException(message: String): Exception(message)