package com.example.noteapp.feature_note.presentation.screen_notes_list

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.R
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var jobGetNotes: Job? = null

    init {
        getNotes(NoteOrder.Title, OrderType.Descending)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.orderType::class == event.orderType::class
                ) {
                    return
                }
                getNotes(event.noteOrder, event.orderType)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrder -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder, orderType: OrderType) {
        jobGetNotes?.cancel()
        jobGetNotes = noteUseCases.getNotesUseCase(noteOrder, orderType)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder,
                    orderType = orderType
                )
            }
            .launchIn(viewModelScope)
    }

    fun onNoteOrderChange(radioGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.rb_title -> {
                onEvent(NotesEvent.Order(NoteOrder.Title, state.value.orderType))
            }
            R.id.rb_date -> {
                onEvent(NotesEvent.Order(NoteOrder.Date, state.value.orderType))
            }
            R.id.rb_color -> {
                onEvent(NotesEvent.Order(NoteOrder.Color, state.value.orderType))
            }
        }
    }

    fun onOrderTypeChange(radioGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.rb_ascending -> {
                onEvent(NotesEvent.Order(state.value.noteOrder, OrderType.Ascending))
            }
            R.id.rb_descending -> {
                onEvent(NotesEvent.Order(state.value.noteOrder, OrderType.Descending))
            }
        }
    }

    fun toggleOrderSection() {
        _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
    }
}