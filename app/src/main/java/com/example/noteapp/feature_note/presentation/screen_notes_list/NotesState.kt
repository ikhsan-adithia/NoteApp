package com.example.noteapp.feature_note.presentation.screen_notes_list

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Title,
    val orderType: OrderType = OrderType.Descending,
    val isOrderSectionVisible: Boolean = false
)