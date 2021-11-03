package com.example.noteapp.feature_note.presentation.screen_notes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.core.util.UiEvent
import com.example.noteapp.databinding.FragmentNotesBinding
import com.example.noteapp.feature_note.domain.adapter.NotesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotesFragment: Fragment(R.layout.fragment_notes) {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<NotesViewModel>()

    private var notesAdapter: NotesAdapter? = null

    override fun onDestroyView() {
        _binding = null
        notesAdapter = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notesAdapter = NotesAdapter.instance()

        _binding = DataBindingUtil.inflate<FragmentNotesBinding>(inflater, R.layout.fragment_notes, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            adapter = notesAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesAdapter?.setOnItemClickListener { noteId ->
            Log.e("log", "onViewCreated: $noteId")
//            findNavController().navigate(
//                R.id.action_notesFragment_to_addEditFragment,
//                bundleOf("noteId" to noteId)
//            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is UiEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, uiEvent.message, Snackbar.LENGTH_LONG).apply {
//                            this.view.setBackgroundColor(requireContext().color(R.color.success))
//                            this.setTextColor(requireContext().color(R.color.white))
//                            this.setActionTextColor(requireContext().color(R.color.white))
                            setAction("Undo") {
                                viewModel.onEvent(NotesEvent.RestoreNote)
                            }
                        }.show()
                    }
                    is UiEvent.ShowToast -> {}
                }
            }
        }
    }

    companion object {
        fun instance() = NotesFragment()
    }
}
