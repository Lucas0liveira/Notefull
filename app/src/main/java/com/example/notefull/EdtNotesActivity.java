package com.example.notefull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EdtNotesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_notes_activity);

        EditText edtTitulo = findViewById(R.id.edtNoteTitulo);
        EditText edtBody = findViewById(R.id.edtNoteBody);
        EditText edtLembrete = findViewById(R.id.edtNoteLembrete);
        EditText edtLocal = findViewById(R.id.edtNoteLocalization);
        Button btnNotification = findViewById(R.id.btnNoteNotification);
        Button btnConfirm = findViewById(R.id.btnNoteCheck);
        Button btnQuit = findViewById(R.id.btnNoteQuit);

        btnNotification.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });
    }
}
