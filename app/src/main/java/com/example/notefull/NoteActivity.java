package com.example.notefull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        ListView lv = findViewById(R.id.listview_notas);
        Button addNote = findViewById(R.id.button4);
        Button logout = findViewById(R.id.button5);
        Button orderByName = findViewById(R.id.button7);
        Button orderByDate = findViewById(R.id.button8);


        addNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewNoteActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        orderByName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        orderByDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }


}
