package com.example.noteapp.core.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.feature_note.presentation.screen_notes_list.NotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var notesFragment: NotesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesFragment = NotesFragment.instance()

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity

            supportFragmentManager.commit {
                notesFragment?.let { fragment ->
                    add(R.id.fc_main, fragment)
                }
            }
        }
    }

    override fun onDestroy() {
        notesFragment = null
        super.onDestroy()
    }
}