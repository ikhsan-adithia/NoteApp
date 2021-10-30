package com.example.noteapp.feature_note.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presentation.screen_notes_list.NotesEvent
import com.example.noteapp.feature_note.presentation.screen_notes_list.NotesViewModel

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class NotesViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = differ.currentList[position]

        holder.binding.note = note
        holder.binding.executePendingBindings()

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(note) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Note) -> Unit)? = null
    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }
}

@BindingAdapter("android:items", "android:viewModel")
fun items(recyclerView: RecyclerView, itemViewModels: List<Note>, viewModel:NotesViewModel) {
    val adapter = getOrCreateAdapter(recyclerView)
    adapter.differ.submitList(itemViewModels)

    adapter.setOnItemClickListener { note ->
        viewModel.onEvent(NotesEvent.DeleteNote(note))
    }
}

private fun getOrCreateAdapter(recyclerView: RecyclerView): NotesAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is NotesAdapter) {
        recyclerView.adapter as NotesAdapter
    } else {
        val notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter
        notesAdapter
    }
}