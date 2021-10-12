package ru.gb.course1.notebook.impl;

import android.annotation.SuppressLint;

import ru.gb.course1.notebook.domain.Note;
import ru.gb.course1.notebook.domain.NoteCashRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteImpl implements NoteCashRepo {
    private List<Note> cashNotes = new ArrayList<>();

    private String initCreateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    @Override
    public void setNotes(List<Note> notes) {
        cashNotes = notes;
    }

    @Override
    public ArrayList<Note> getNotes() {
        return new ArrayList<>(cashNotes);
    }

    @Override
    public int createNote(Note note) {
        int newId;
        if (cashNotes.isEmpty()) {
            newId = 0;
        } else {
            newId = cashNotes.get(0).getIdNote();
        }
        for (int i = 0; i < cashNotes.size(); i++) {
            if (newId == cashNotes.get(i).getIdNote()) {
                newId++;
            }
        }
        note.setIdNote(newId);
        note.setTimeCreate(initCreateTime());
        cashNotes.add(note);
        return newId;
    }

    @Override
    public Note readNote(int idNote) {
        for (Note note : cashNotes) {
            if (note.getIdNote() == idNote) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<Note> updateNote(Note note) {
        for (int i = 0; i < cashNotes.size(); i++) {
            if (cashNotes.get(i).getIdNote() == note.getIdNote()) {
                cashNotes.set(i, note);
            }
        }
        return cashNotes;
    }

    @Override
    public int deleteNote(int position) {
        int idDeleteNote = cashNotes.get(position).getIdNote();
        cashNotes.remove(position);
        return idDeleteNote;
    }
}
