package com.example.notefull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class EdtNotesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        long id = -1;
        if(b != null)
            id = b.getInt("id");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_notes_activity);

        final EditText edtTitulo = findViewById(R.id.edtNoteTitulo);
        final EditText edtBody = findViewById(R.id.edtNoteBody);
        final EditText edtLembrete = findViewById(R.id.edtNoteLembrete);
        final EditText edtLocal = findViewById(R.id.edtNoteLocalization);
        Button btnNotification = findViewById(R.id.btnNoteNotification);
        Button btnConfirm = findViewById(R.id.btnNoteCheck);
        Button btnQuit = findViewById(R.id.btnNoteQuit);

        btnNotification.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        final long finalId = id;
        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view){
                DatabaseHelper db = new DatabaseHelper(view.getContext());
                db.RemoveNote(finalId);
                Note note = new Note();
                note.setTitle(edtTitulo.getText().toString());
                note.setBody(edtBody.getText().toString());
                note.setTimer(edtLembrete.getText().toString());
                note.setLat(edtLocal.getText().toString());
                note.setLg(edtLocal.getText().toString());
                note.setDate(LocalDateTime.now().toString());
                db.addNote(note);

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
