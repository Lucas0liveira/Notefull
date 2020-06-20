package com.example.notefull;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class EdtNotesActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        long id = -1;
        long userId = -1;
        if(b != null) {
            id = b.getInt("id");
            userId = b.getLong("userId");
        }

        createNotificationChannel();

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
                Toast.makeText(EdtNotesActivity.this, "Lembrete Definido!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EdtNotesActivity.this, Lembrete.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(EdtNotesActivity.this,0,intent,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                long timeSeconds = 1000 * 1;

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + timeSeconds, pendingIntent);

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
                        Toast.makeText(EdtNotesActivity.this, "Nota editada com sucesso.", Toast.LENGTH_SHORT).show();

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
    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name= "Lembrete";
            String description = "Canal de lembrete";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
