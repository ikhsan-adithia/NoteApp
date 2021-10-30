package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            // this have to be put in string res
            throw InvalidNoteException("Title cannot be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content cannot be empty.")
        }
        repository.insertNote(note)
    }
}