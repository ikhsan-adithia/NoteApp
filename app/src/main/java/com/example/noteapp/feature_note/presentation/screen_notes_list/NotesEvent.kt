package com.example.noteapp.feature_note.presentation.screen_notes_list

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder, val orderType: OrderType): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrder: NotesEvent()
}
