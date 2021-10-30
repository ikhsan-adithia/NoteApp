package com.example.noteapp.feature_note.domain.util

sealed class NoteOrder() {
    object Title: NoteOrder()
    object Date : NoteOrder()
    object Color : NoteOrder()
}
