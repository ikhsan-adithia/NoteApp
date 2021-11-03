package com.example.noteapp.feature_note.presentation.screen_add_edit_note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddEditBinding

class AddEditFragment: Fragment(R.layout.fragment_add_edit) {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("noteId", -1)?.let {
            Log.e("AddEditFragment", "onViewCreated: noteId $it")
            Toast.makeText(requireContext(), "onViewCreated: noteId $it", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun instance() = AddEditFragment()
    }
}