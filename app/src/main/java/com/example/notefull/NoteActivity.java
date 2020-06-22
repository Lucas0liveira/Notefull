package com.example.notefull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    ListView lv = findViewById(R.id.listview_notas);
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        Button addNote = findViewById(R.id.button4);
        Button logout = findViewById(R.id.button5);
        Button orderByName = findViewById(R.id.button7);
        Button orderByDate = findViewById(R.id.button8);

        Bundle b = getIntent().getExtras();
        final long userId = b.getLong("userId");
        Log.d(null, "USERID:" + userId);

        DatabaseHelper dbh = new DatabaseHelper(this);
        final List<Note> notes = dbh.getAllNotes(userId);

        ArrayList<String> noteNames =  new ArrayList<>();
        for(Note n : notes){
            noteNames.add(n.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteNames);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                Intent intent = new Intent(view.getContext(), EdtNotesActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", notes.get(i).getId());
                b.putLong("userId", userId);
                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewNoteActivity.class);
                Bundle b = new Bundle();
                b.putLong("userId", userId);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        orderByName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseHelper dbh = new DatabaseHelper(view.getContext());
                final List<Note> notes = dbh.orderByName(userId);

                ArrayList<String> noteNames =  new ArrayList<>();
                for(Note n : notes){
                    noteNames.add(n.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, noteNames);
                lv.setAdapter(adapter);
            }
        });

        orderByDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseHelper dbh = new DatabaseHelper(view.getContext());
                final List<Note> notes = dbh.getAllNotes(userId);

                ArrayList<String> noteNames =  new ArrayList<>();
                for(Note n : notes){
                    noteNames.add(n.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, noteNames);
                lv.setAdapter(adapter);
            }
        });
    }


}
