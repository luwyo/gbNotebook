package ru.gb.course1.notebook.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.course1.notebook.R;
import ru.gb.course1.notebook.domain.Note;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class NoteEditFragment extends Fragment {
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private MaterialToolbar appMaterialToolBar;
    public static final String INFO_NOTE_KEY = "info_note_key";
    public static String CHANGE_NOTE_KEY = "change_note_key";
    public final static String NOTE_EDIT_FRAGMENT = "NOTE_EDIT_FRAGMENT";
    private Note note;
    private boolean isEmptyNote = false;
    private Controller controller;

    public NoteEditFragment(MaterialToolbar appMaterialToolBar) {
        this.appMaterialToolBar = appMaterialToolBar;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity doesn't have impl NoteEditFragment.Controller interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        getNoteFromNotesList();
        fillEditTexts();
        getNoteFromNotesList();
        initialiseNavigationIconOnClick();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_create_time && !isEmptyNote) {
            initialiseChangeCreateTimeDataPicker();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialiseChangeCreateTimeDataPicker() {
        AtomicReference<String> data = new AtomicReference<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = formatter.parse(note.getTimeCreate());
            if (date != null) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.select_data_create)
                        .setSelection(date.getTime())
                        .build();

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText(R.string.select_time_created)
                        .setHour(Integer.parseInt(note.getTimeCreate().split(" ", 2)[1].split(":", 2)[0]))
                        .setMinute(Integer.parseInt(note.getTimeCreate().split(" ", 2)[1].split(":", 2)[1]))
                        .build();

                datePicker.show(getParentFragmentManager(), "data picker");

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(selection);
                    timePicker.show(getParentFragmentManager(), "time picker");
                    data.set(format.format(calendar.getTime()));
                });

                timePicker.addOnPositiveButtonClickListener((l) -> {
                    @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute());
                    data.set(data.get() + " " + time);
                    note.setTimeCreate(data.get());
                });
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View view) {
        titleEditText = view.findViewById(R.id.title_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
    }

    private void getNoteFromNotesList() {
        Bundle args = getArguments();
        if (args != null) {
            note = args.getParcelable(INFO_NOTE_KEY);
        }
    }

    private void fillEditTexts() {
        if (note.getTitle().isEmpty() && note.getDescription().isEmpty()) {
            isEmptyNote = true;
        } else {
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
        }
    }

    private void initialiseNavigationIconOnClick() {
        appMaterialToolBar.setNavigationOnClickListener(l -> returnToNotesList());
    }

    private void returnToNotesList() {
        if (titleEditText.getText() != null && descriptionEditText.getText() != null) {
            if (isEmptyNote) {
                note = new Note(titleEditText.getText().toString(), descriptionEditText.getText().toString());
                controller.returnNewNote(note);
            } else {
                note.setTitle(titleEditText.getText().toString());
                note.setDescription(descriptionEditText.getText().toString());
                controller.returnChangedNote(note);
            }
        }
    }

    public static NoteEditFragment newInstance(Note note, MaterialToolbar topAppBar) {
        NoteEditFragment noteEditFragment = new NoteEditFragment(topAppBar);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INFO_NOTE_KEY, note);
        noteEditFragment.setArguments(bundle);
        return noteEditFragment;
    }

    public void onBackPressed() {
        returnToNotesList();
    }

    interface Controller {
        void returnNewNote(Note note);

        void returnChangedNote(Note note);
    }
}
