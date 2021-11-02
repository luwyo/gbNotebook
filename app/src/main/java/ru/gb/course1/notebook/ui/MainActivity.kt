package ru.gb.course1.notebook.ui

import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.notebook.ui.NotesListFragment
import ru.gb.course1.notebook.ui.NoteEditFragment
import com.google.android.material.appbar.MaterialToolbar
import android.os.Bundle
import ru.gb.course1.notebook.R
import ru.gb.course1.notebook.domain.Note

class MainActivity : AppCompatActivity(), NotesListFragment.Controller, NoteEditFragment.Controller {
    private var topAppBar: MaterialToolbar? = null
    private var notesListFragment: NotesListFragment? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseTopAppBar()
        launchNotesListFragment()
    }

    private fun initialiseTopAppBar() {
        topAppBar = findViewById(R.id.top_app_bar)
        setSupportActionBar(topAppBar)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag(NoteEditFragment.NOTE_EDIT_FRAGMENT) != null) {
            val noteEditFragment = supportFragmentManager.findFragmentByTag(NoteEditFragment.NOTE_EDIT_FRAGMENT) as NoteEditFragment?
            noteEditFragment?.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    private fun launchNotesListFragment() {
        notesListFragment = NotesListFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_frame_layout, notesListFragment!!, NotesListFragment.NOTES_LIST_FRAGMENT)
                .commit()
    }

    override fun launchEditNoteScreen(note: Note) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_frame_layout, NoteEditFragment.newInstance(note, topAppBar), NoteEditFragment.NOTE_EDIT_FRAGMENT)
                .addToBackStack("")
                .commit()
        topAppBar!!.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)
        topAppBar!!.title = ""
    }

    override fun returnNewNote(note: Note) {
        notesListFragment = supportFragmentManager.findFragmentByTag(NotesListFragment.NOTES_LIST_FRAGMENT) as NotesListFragment?
        if (notesListFragment != null) {
            supportFragmentManager.popBackStack()
            notesListFragment!!.setNewNote(note)
            topAppBar!!.title = getString(R.string.app_name)
        }
    }

    override fun returnChangedNote(note: Note) {
        notesListFragment = supportFragmentManager.findFragmentByTag(NotesListFragment.NOTES_LIST_FRAGMENT) as NotesListFragment?
        if (notesListFragment != null) {
            supportFragmentManager.popBackStack()
            notesListFragment!!.setChangedNote(note)
            topAppBar!!.title = getString(R.string.app_name)
        }
    }
}