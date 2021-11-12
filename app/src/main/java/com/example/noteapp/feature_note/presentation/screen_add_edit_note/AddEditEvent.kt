package com.example.noteapp.feature_note.presentation.screen_add_edit_note

sealed class AddEditEvent {
    object SaveNote: AddEditEvent()
    data class TitleChange(var noteTitle: String): AddEditEvent()
    data class ContentChange(val noteContent: String): AddEditEvent()
    data class ColorChange(val colorId: Int): AddEditEvent()
}
