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
    private static final String NOTE_COLUMN_ID = "id";
    private static final String NOTE_COLUMN_TITLE = "title";
    private static final String NOTE_COLUMN_BODY = "body";
    private static final String NOTE_COLUMN_FOLDER = "folder";
    private static final String NOTE_COLUMN_TIMER = "timer";
    private static final String NOTE_COLUMN_LATITUDE = "lat";
    private static final String NOTE_COLUMN_LONGITUDE = "lg";
    private static  final  String NOTE_COLUMN_DATE =  "date";
    //folder
    private static final String FOLDER_TABLE_NAME = "folders";
    private static final String FOLDER_COLUMN_ID = "id";
    private static final String FOLDER_COLUMN_TITLE = "title";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_COLUMN_NAME + "TEXT NOT NULL, " +
                USER_COLUMN_EMAIL + "TEXT NOT NULL, " +
                USER_COLUMN_PASSWORD + "TEXT NOT NULL " +
                ")";

        String CREATE_NOTES_TABLE = "CREATE TABLE " + NOTE_TABLE_NAME + "(" +
                NOTE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOTE_COLUMN_TITLE + "TEXT NOT NULL, " +
                NOTE_COLUMN_BODY + "TEXT NOT NULL, " +
                NOTE_COLUMN_FOLDER + "INTEGER REFERENCES " + FOLDER_TABLE_NAME + "," +
                NOTE_COLUMN_TIMER + "TEXT NOT NULL, " +
                NOTE_COLUMN_LATITUDE + "TEXT NOT NULL, " +
                NOTE_COLUMN_LONGITUDE + "TEXT NOT NULL " +
                ")";

        String CREATE_FOLDERS_TABLE = "CREATE TABLE " + FOLDER_TABLE_NAME + "(" +
                FOLDER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FOLDER_COLUMN_TITLE + "TEXT NOT NULL " +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_FOLDERS_TABLE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryUser = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        String queryNote = "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME;
        String queryFolder = "DROP TABLE IF EXISTS " + FOLDER_TABLE_NAME;
        db.execSQL(queryUser);
        db.execSQL(queryNote);
        db.execSQL(queryFolder);
        this.onCreate(db);
    }

    public boolean SignIn(User user) {

        String SELECT_USER =
                String.format("SELECT * FROM %s WHERE email = %s", USER_TABLE_NAME, user.getEmail());
        SQLiteDatabase dbcheck = getReadableDatabase();
        Cursor cursor = dbcheck.rawQuery(SELECT_USER, null);

        if (!cursor.equals(null)) {
            //email já está cadastrado
            return false;
        }

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_NAME, user.getName());
            values.put(USER_COLUMN_EMAIL, user.getName());
            values.put(USER_COLUMN_PASSWORD, user.getName());

            db.insertOrThrow(USER_TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(e.getStackTrace().toString(), "Erro ao cadastrar usuário!");
            return false;
        }
        return true;
    }

    public boolean Login(User user) {

        String SELECT_USER =
                String.format("SELECT * FROM %s WHERE email = %s", USER_TABLE_NAME, user.getEmail());
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_USER, null);

        try {
            if (cursor.moveToFirst()) {
                String checkPassword = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD));
                if (!checkPassword.equals(user.getPassword())) {
                    //Senha incorreta
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d(e.getStackTrace().toString(), "Erro ao listar celulares");
            return false;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return false;
        }
    }

    public void addNote(Note note) {
        String folderID = "";
        SQLiteDatabase dbfolder = getReadableDatabase();
        String GET_FOLDER_ID = "SELECT id FROM " + FOLDER_TABLE_NAME +
                " WHERE " + FOLDER_COLUMN_TITLE + "=" + note.getFolder();
        Cursor cursor = dbfolder.rawQuery(GET_FOLDER_ID, null);
        try {
            if (cursor.moveToFirst())
                folderID = cursor.getString(cursor.getColumnIndex(FOLDER_COLUMN_ID));
        } catch (Exception e) {
            Log.d(null, "Erro ao associar pasta à nova nota");
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(NOTE_COLUMN_TITLE, note.getTitle());
            values.put(NOTE_COLUMN_BODY, note.getBody());
            values.put(NOTE_COLUMN_FOLDER, note.getFolder());
            values.put(NOTE_COLUMN_TIMER, note.getTimer());
            values.put(NOTE_COLUMN_LATITUDE, note.getLat());
            values.put(NOTE_COLUMN_LONGITUDE, note.getLg());
            values.put(NOTE_COLUMN_DATE, note.getDate());
            db.insertOrThrow(NOTE_TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(null, "Erro ao inserir uma nova nota");
        }
    }

    public long RemoveNote(String body) {
        SQLiteDatabase db = getWritableDatabase();
        long idRemoved = -1;
        String[] args = {String.valueOf(body)};
        idRemoved = db.delete(NOTE_TABLE_NAME, NOTE_COLUMN_BODY +"=?", args);
        return idRemoved;
    }

    public void addFolder(Folder folder) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(FOLDER_COLUMN_TITLE, folder.getTitle());
            db.insertOrThrow(FOLDER_TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(null, "Erro ao inserir uma nova pasta");
        }
    }

    public long removeFolder(String title) {
        SQLiteDatabase db = getWritableDatabase();
        long idRemoved = -1;
        String[] args = {String.valueOf(title)};
        idRemoved = db.delete(FOLDER_TABLE_NAME, FOLDER_COLUMN_TITLE +"=?", args);
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
                note.setFolder(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_FOLDER)));
                note.setLat(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LATITUDE)));
                note.setLg(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LONGITUDE)));
                note.setTimer(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TIMER)));
            }
        }catch (Exception e){
            Log.d(null, "Erro ao obter anotação");
        }
        return note;
    }

    public Folder getFolder(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String GET_FOLDER = "SELECT * FROM " + FOLDER_TABLE_NAME +
                "WHERE " + FOLDER_COLUMN_ID + "=" + id;
        Cursor cursor = db.rawQuery(GET_FOLDER, null);
        Folder folder = new Folder();

        try{
            folder.setTitle(cursor.getString(cursor.getColumnIndex(FOLDER_COLUMN_TITLE)));
        }catch (Exception e){
            Log.d(null, "Erro ao obter pasta");
        }
        return folder;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String GET_NOTES = "SELECT * FROM " + NOTE_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_NOTES, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note();
                    note.setTitle(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TITLE)));
                    note.setBody(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_BODY)));
                    note.setDate(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_DATE)));
                    note.setFolder(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_FOLDER)));
                    note.setLat(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LATITUDE)));
                    note.setLg(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_LONGITUDE)));
                    note.setTimer(cursor.getString(cursor.getColumnIndex(NOTE_COLUMN_TIMER)));
                    notes.add(note);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(null, "Erro ao obter lista de notas");
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notes;
    }

    public List<Folder> getAllFolders() {
        List<Folder> folders = new ArrayList<>();
        String GET_FOLDERS = "SELECT * FROM " + FOLDER_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_FOLDERS, null);
        try{
            Folder folder = new Folder();
            folder.setTitle(cursor.getString(cursor.getColumnIndex(FOLDER_COLUMN_TITLE)));
            folders.add(folder);
        }catch (Exception e){
            Log.d(null, "Erro ao obter lista de pastas");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return folders;
    }

}
