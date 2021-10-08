package ru.gb.course1.notebook.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.course1.notebook.R;

public class NoteVh extends RecyclerView.ViewHolder {
    public NoteVh(@NonNull View itemView) {
        super(itemView);
    }

    public LinearLayout noteItemView = itemView.findViewById(R.id.note_item_linear);
    public TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    public TextView detailTextView = itemView.findViewById(R.id.detail_text_view);
}
