package com.example.noteapp.core.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    data class ShowToast(val message: String): UiEvent()
}
