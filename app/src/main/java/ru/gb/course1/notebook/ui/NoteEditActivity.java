package ru.gb.course1.notebook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import ru.gb.course1.notebook.R;
import ru.gb.course1.notebook.domain.NoteEntity;

import static ru.gb.course1.notebook.utils.Constants.KEY_SAVEINSTANCE;

public class NoteEditActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText detailEditText;
    private Button saveButton;
    private NoteEntity note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        note = (NoteEntity) getIntent().getParcelableExtra(KEY_SAVEINSTANCE);

        titleEditText = findViewById(R.id.title_edit_view);
        detailEditText = findViewById(R.id.detail_edit_view);
        saveButton = findViewById(R.id.save_button);

        if (note != null){
            Log.d("mylogs", "activity not null");
            titleEditText.setText(note.getTitle());
            detailEditText.setText(note.getDetails());
        } else{
            Log.d("mylogs", "activity null");
        }



        saveButton.setOnClickListener(v -> {
            NoteEntity noteEntity = new NoteEntity(
                    titleEditText.getText().toString(),
                    detailEditText.getText().toString()
            );
            //todo???
        });
    }
}
