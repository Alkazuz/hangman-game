package ufms.hangman.game.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance = null;
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] tables = new String[] {
                "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY, name TEXT, photo BLOB)",
                "CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY, word TEXT, difficulty INTEGER)",
                "CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY, player_id INTEGER, score INTEGER, word_id INTEGER, difficulty INTEGER, status INTEGER, FOREIGN KEY(player_id) REFERENCES players(id), FOREIGN KEY(word_id) REFERENCES words(id))"
        };
        String CREATE_TABLE_EXEMPLO = "CREATE TABLE TabelaExemplo (id INTEGER PRIMARY KEY, nome TEXT)";
        db.execSQL(CREATE_TABLE_EXEMPLO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor select(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }
}
