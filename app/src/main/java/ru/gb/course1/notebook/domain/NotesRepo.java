package ru.gb.course1.notebook.domain;

import androidx.annotation.Nullable;

import java.util.List;

public interface NotesRepo {
    List<NoteEntity> getNotes();
    @Nullable
    Integer createNote(NoteEntity note);
    boolean deleteNote(int id);
    boolean updateNote(int id, NoteEntity note);
}
