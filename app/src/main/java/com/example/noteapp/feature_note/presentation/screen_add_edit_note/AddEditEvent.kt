package com.example.noteapp.feature_note.presentation.screen_add_edit_note

import com.example.noteapp.feature_note.domain.model.Note

sealed class AddEditEvent {
    data class SaveNote(val note: Note): AddEditEvent()
    data class ChangeColor(val colorId: Int): AddEditEvent()
}
