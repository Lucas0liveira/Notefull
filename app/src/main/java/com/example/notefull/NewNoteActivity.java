package com.example.notefull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;

public class NewNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long timeSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_activity);

        final EditText edtTitulo = findViewById(R.id.edtTituloNN);
        final EditText edtBody = findViewById(R.id.edtNewAnotation);
        final Spinner edtLembrete = findViewById(R.id.edtLembrete);
        final Button btnNotification = findViewById(R.id.btnNotification);
        Button btnConcluido = findViewById(R.id.btnCheck);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horarios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtLembrete.setAdapter(adapter);
        edtLembrete.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        final long userId = b.getLong("userId");

        btnNotification.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                btnNotification.setBackground(getResources().getDrawable(R.mipmap.ic_notification));


                Toast.makeText(NewNoteActivity.this, "Lembrete Definido!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), Lembrete.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);

                Intent intentPage = new Intent(NewNoteActivity.this,
                        Lembrete.class);
                PendingIntent pendingIntentPage = PendingIntent.getActivity(
                        NewNoteActivity.this, 0, intentPage,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + timeSeconds, pendingIntent);
            }
        });
        btnConcluido.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(view.getContext());
                Note note = new Note();
                note.setTitle(edtTitulo.getText().toString());
                note.setBody(edtBody.getText().toString());

                db.addNote(note, userId);

                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                Bundle b = new Bundle();
                b.putLong("userId", userId);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                Toast.makeText(NewNoteActivity.this, "Nota criada com sucesso.", Toast.LENGTH_SHORT).show();
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