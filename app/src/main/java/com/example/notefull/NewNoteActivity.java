package com.example.notefull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.time.LocalDateTime;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_activity);

        final EditText edtTitulo = findViewById(R.id.edtTituloNN);
        final EditText edtBody = findViewById(R.id.edtNewAnotation);
        final EditText edtLembrete = findViewById(R.id.edtLembrete);
        final EditText edtLocalization = findViewById(R.id.edtLocalization);
        Button btnNotification = findViewById(R.id.btnNotification);
        Button btnConcluido = findViewById(R.id.btnCheck);

        btnConcluido.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(view.getContext());
                Note note = new Note();
                note.setTitle(edtTitulo.getText().toString());
                note.setBody(edtBody.getText().toString());
                note.setTimer(edtLembrete.getText().toString());
                note.setLat(edtLocalization.getText().toString());
                note.setLg(edtLocalization.getText().toString());
                LocalDateTime date = LocalDateTime.now();
                Log.d(null, date.toString());
                note.setDate(date.toString());

                db.addNote(note);

                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                startActivity(intent);
            }
        });




    }

}