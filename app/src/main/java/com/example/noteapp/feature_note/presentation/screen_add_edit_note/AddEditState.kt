package com.example.noteapp.feature_note.presentation.screen_add_edit_note

import com.example.noteapp.feature_note.domain.model.Note

data class AddEditState(
    val note: Note = Note(
        title = "",
        content = "",
        timestamp = System.currentTimeMillis(),
        color = Note.noteColors.random()
    )
)
