package com.example.noteapp.feature_note.presentation.screen_add_edit_note

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.R
import com.example.noteapp.core.util.UiEvent
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val _state = MutableStateFlow(AddEditState())
    val state: StateFlow<AddEditState> = _state

    private val _noteColor = MutableStateFlow(Note.noteColors.random())
    val noteColor: StateFlow<Int> = _noteColor

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.let { note ->
                        currentNoteId = note.id
                        _noteColor.value = note.color
                        _state.value = state.value.copy(note = note)
                    }
                }
            }
        }
    }

//    fun onEvent(event: AddEditEvent) {
//        when (event) {
//            is AddEditEvent.TitleChange -> {
//                Log.e("AddEditViewModel", "onEvent: ${event.noteTitle}")
//            }
//            is AddEditEvent.ContentChange -> {}
//            is AddEditEvent.ColorChange -> {}
//            is AddEditEvent.SaveNote -> {}
//        }
//    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                state.value.note.also { note ->
                    noteUseCases.addNoteUseCase(Note(
                        title = note.title,
                        content = note.content,
                        timestamp = System.currentTimeMillis(),
                        color = noteColor.value,
                        id = currentNoteId
                    ))
                }
            } catch (e: InvalidNoteException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Couldn't save note"))
            }
        }
    }

    fun onColorChange(radioGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.rb_blue -> {
                _noteColor.value = R.color.rb_blue
            }
            R.id.rb_purple -> {
                _noteColor.value = R.color.rb_purple
            }
            R.id.rb_green -> {
                _noteColor.value = R.color.rb_green
            }
            R.id.rb_magenta -> {
                _noteColor.value = R.color.rb_magenta
            }
        }
    }

}