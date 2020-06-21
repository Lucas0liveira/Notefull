package com.example.notefull;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notefull.db";
    //user
    private static final String USER_TABLE_NAME = "users";
    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PASSWORD = "password";
    //note
    private static final String NOTE_TABLE_NAME = "notes";
    private static final String NOTE_COLUMN_USER = "user";
    private static final String NOTE_COLUMN_ID = "id";
    private static final String NOTE_COLUMN_TITLE = "title";
    private static final String NOTE_COLUMN_BODY = "body";
    private static final String NOTE_COLUMN_TIMER = "timer";
    private static final String NOTE_COLUMN_LATITUDE = "lat";
    private static final String NOTE_COLUMN_LONGITUDE = "lg";
    private static final String NOTE_COLUMN_DATE =  "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY," +
                USER_COLUMN_NAME + " TEXT NOT NULL, " +
                USER_COLUMN_EMAIL + " TEXT NOT NULL, " +
                USER_COLUMN_PASSWORD + " TEXT NOT NULL " +
                ")";

        String CREATE_NOTES_TABLE = "CREATE TABLE " + NOTE_TABLE_NAME + "(" +
                NOTE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                NOTE_COLUMN_USER + " INTEGER REFERENCES " + USER_TABLE_NAME + ", " +
                NOTE_COLUMN_TITLE + " TEXT NOT NULL, " +
                NOTE_COLUMN_BODY + " TEXT NOT NULL, " +
                NOTE_COLUMN_TIMER + " TEXT NOT NULL, " +
                NOTE_COLUMN_LATITUDE + " TEXT NOT NULL, " +
                NOTE_COLUMN_LONGITUDE + " TEXT NOT NULL, " +
                NOTE_COLUMN_DATE + " TEXT NOT NULL " +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryUser = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        String queryNote = "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME;
        db.execSQL(queryUser);
        db.execSQL(queryNote);
        this.onCreate(db);
    }

    public long SignIn(User user) {

        String SELECT_USER =String.format("SELECT * FROM  %s WHERE %s =?", USER_TABLE_NAME, USER_COLUMN_EMAIL);
        SQLiteDatabase dbcheck = getReadableDatabase();
        Cursor cursor = dbcheck.rawQuery(SELECT_USER, new String[]{String.valueOf(user.getEmail())});

        if (cursor.moveToFirst()) {
            Log.d(null, "DB: Email já cadastrado!");
            return -1;
        }

        SQLiteDatabase db = getWritableDatabase();
        long userId = -2;
        try {
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_NAME, user.getName());
            values.put(USER_COLUMN_EMAIL, user.getEmail());
            values.put(USER_COLUMN_PASSWORD, user.getPassword());

            userId = db.insertOrThrow(USER_TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(e.getStackTrace().toString(), "DB: Erro ao cadastrar usuário!");
            return -2;
        }
        Log.d(null, "DB: Cadastro realizado com sucesso!");
        return userId;
    }

    public long Login(User user) {

        String SELECT_USER =
                String.format("SELECT * FROM %s WHERE %s =?", USER_TABLE_NAME, USER_COLUMN_EMAIL);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_USER, new String[]{String.valueOf(user.getEmail())});

        long userId = -3;
        try {
            if (cursor.moveToFirst()) {
                String checkPassword = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD));
                userId = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID));
                if (!checkPassword.equals(user.getPassword())) {
                    Log.d(null, "DB: Senha ou email incorreto!");
                    return -3;
                } else {
                    return userId;
                }
            }
        } catch (Exception e) {
            Log.d(e.getStackTrace().toString(), "DB: Erro ao fazer login");
            return -1;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        Log.d(null, "DB: Email não cadastrado!");
        return -1;
    }

    public void addNote(Note note, long userId) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(NOTE_COLUMN_TITLE, note.getTitle());
            values.put(NOTE_COLUMN_BODY, note.getBody());
            values.put(NOTE_COLUMN_TIMER, note.getTimer());
            values.put(NOTE_COLUMN_LATITUDE, note.getLat());
            values.put(NOTE_COLUMN_LONGITUDE, note.getLg());
            values.put(NOTE_COLUMN_DATE, note.getDate());
            values.put(NOTE_COLUMN_USER, userId);
            System.out.println("DB: Nota adicionada, id:" + db.insertOrThrow(NOTE_TABLE_NAME, null, values));
        } catch (Exception e) {
            Log.d(null, "DB: Erro ao inserir uma nova nota");
        }
    }

    public long RemoveNote(long id) {
        SQLiteDatabase db = getWritableDatabase();
        long idRemoved = -1;
        String[] args = {String.valueOf(id)};
        idRemoved = db.delete(NOTE_TABLE_NAME, NOTE_COLUMN_BODY +"=?", args);
        return idRemoved;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String GET_NOTE = "SELECT * FROM" + NOTE_TABLE_NAME +
                "WHERE" + NOTE_COLUMN_ID + "=" + id;
        Cursor cursor = db.rawQuery(GET_NOTE, null);
        Note note = new Note();

        try{
            if(cursor.moveToFirst()){
                note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TITLE)));
                note.setBody(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_BODY)));
                note.setDate(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_DATE)));
                note.setLat(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LATITUDE)));
                note.setLg(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LONGITUDE)));
                note.setTimer(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TIMER)));
            }
        }catch (Exception e){
            Log.d(null, "DB: Erro ao obter anotação");
        }
        return note;
    }

    public List<Note> getAllNotes(long userId) {
        List<Note> notes = new ArrayList<>();
        String GET_NOTES = "SELECT * FROM " + NOTE_TABLE_NAME + " WHERE " + NOTE_COLUMN_USER + "=?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_NOTES, new String[]{String.valueOf(userId)});
        try{
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note();
                    note.setId(cursor.getLong(cursor.getColumnIndex(NOTE_COLUMN_ID)));
                    note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TITLE)));
                    note.setBody(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_BODY)));
                    note.setDate(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_DATE)));
                    note.setLat(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LATITUDE)));
                    note.setLg(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LONGITUDE)));
                    note.setTimer(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TIMER)));
                    notes.add(note);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(null, "DB: Erro ao obter lista de notas");
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notes;
    }

}
