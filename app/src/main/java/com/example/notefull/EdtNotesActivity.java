package com.example.notefull;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class EdtNotesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long timeSeconds;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        long id = -1;
        final long userId = b.getLong("userId");
        if (b != null)
            id = b.getInt("id");

        createNotificationChannel();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_notes_activity);


        final EditText edtTitulo = findViewById(R.id.edtNoteTitulo);
        final EditText edtBody = findViewById(R.id.edtNoteBody);
        final Spinner edtLembrete = findViewById(R.id.edtNoteLembrete);
        final EditText edtLocal = findViewById(R.id.edtNoteLocalization);
        Button btnNotification = findViewById(R.id.btnNoteNotification);
        Button btnConfirm = findViewById(R.id.btnNoteCheck);
        Button btnQuit = findViewById(R.id.btnNoteQuit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horarios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtLembrete.setAdapter(adapter);
        edtLembrete.setOnItemSelectedListener(this);


        DatabaseHelper db = new DatabaseHelper(this);
        Note note = db.getNote(id);

        edtTitulo.setText(note.getTitle());
        edtBody.setText(note.getBody());

        btnNotification.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(EdtNotesActivity.this, "Lembrete Definido!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), Lembrete.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);

                Intent intentPage = new Intent(EdtNotesActivity.this,
                        Lembrete.class);
                PendingIntent pendingIntentPage = PendingIntent.getActivity(
                        EdtNotesActivity.this, 0, intentPage,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + timeSeconds, pendingIntent);
            }
        });


        final long finalId = id;
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(view.getContext());
                db.RemoveNote(finalId);
                Note note = new Note();
                note.setTitle(edtTitulo.getText().toString());
                note.setBody(edtBody.getText().toString());
                note.setLat(edtLocal.getText().toString());
                note.setLg(edtLocal.getText().toString());
                db.addNote(note, userId);
                Toast.makeText(EdtNotesActivity.this, "Nota editada.", Toast.LENGTH_SHORT).show();

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }





    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lembrete";
            String description = "Canal de lembrete";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        if (position == 0){
            timeSeconds = 1000 * 10;
        }
        if (position == 1){
            timeSeconds = 1000 * 30;
        }
        if (position == 2){
            timeSeconds = 1000 * 60;
        }
        if (position == 3){
            timeSeconds = 1000 * 120;
        }
        if (position == 4){
            timeSeconds = 1000 * 300;
        }
        if (position == 5){
            timeSeconds = 1000 * 600;
        }if (position == 6){
            timeSeconds = 1000 * 900;
        }if (position == 7){
            timeSeconds = 1000 * 1800;
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
