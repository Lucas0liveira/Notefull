package com.example.notefull;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static  final  int DATABASE_VERSION = 1;
    private static  final  String DATABASE_NAME = "notefull.db";
    //user
    private static  final  String USER_TABLE_NAME = "users";
    private static  final  String USER_COLUMN_ID = "id";
    private static  final  String USER_COLUMN_NAME = "name";
    private static  final  String USER_COLUMN_EMAIL = "email";
    private static  final  String USER_COLUMN_PASSWORD = "password";
    //note
    private static  final  String NOTE_TABLE_NAME = "notes";
    private static  final  String NOTE_COLUMN_ID = "id";
    private static  final  String NOTE_COLUMN_TITLE = "title";
    private static  final  String NOTE_COLUMN_BODY = "body";
    private static  final  String NOTE_COLUMN_FOLDER = "folder";
    private static  final  String NOTE_COLUMN_TIMER = "timer";
    private static  final  String NOTE_COLUMN_LATITUDE = "lat";
    private static  final  String NOTE_COLUMN_LONGITUDE = "lg";
    //folder
    private static  final  String FOLDER_TABLE_NAME = "folders";
    private static  final  String FOLDER_COLUMN_ID = "id";
    private static  final  String FOLDER_COLUMN_TITLE = "title";

    public DatabaseHelper(Context context){
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

        String CREATE_FOLDERS_TABLE = "CREATE TABLE " +FOLDER_TABLE_NAME+ "(" +
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
        String queryUser = "DROP TABLE IF EXISTS "+ USER_TABLE_NAME;
        String queryNote = "DROP TABLE IF EXISTS "+ NOTE_TABLE_NAME;
        String queryFolder = "DROP TABLE IF EXISTS "+ FOLDER_TABLE_NAME;
        db.execSQL(queryUser);
        db.execSQL(queryNote);
        db.execSQL(queryFolder);
        this.onCreate(db);
    }

    public void SignIn(){

    }

    public void Login(){

    }

    public void addNote(){

    }

    public void EditNote(){

    }

    public void RemoveNote(){

    }

    public void addFolder(){

    }

    public void editFolder(){

    }

    public void removeFolder(){

    }

    public void getNotes(){

    }

    public void getFolders(){

    }

}
