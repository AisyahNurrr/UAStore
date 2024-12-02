package com.praktikum.connectdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biodatadiri.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel biodata
        String sql = "CREATE TABLE biodata (" +
                "no INTEGER PRIMARY KEY, " +
                "nama TEXT NOT NULL, " +
                "tgl TEXT NOT NULL, " +
                "jk TEXT NOT NULL, " +
                "alamat TEXT NOT NULL);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika ada
        db.execSQL("DROP TABLE IF EXISTS biodata");
        onCreate(db); // Membuat tabel baru
    }
}
