package com.pruebas.tabs.pruebatabs.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pruebas.tabs.pruebatabs.Model.User;

/**
 * Created by Kevin on 07/05/2016.
 */
public class SqliteDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BDUser";


    public SqliteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String tableNotes   = "CREATE TABLE MYUSER(";
        tableNotes += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        tableNotes += "IDMYUSER INTEGER NOT NULL, ";
        tableNotes += "NAME TEXT NOT NULL, ";
        tableNotes += "PASSWORD TEXT NOT NULL, ";
        tableNotes += "USERNAME TEXT NOT NULL) ";

        // Nos permite ejecutar una sentencia de SQL
        db.execSQL(tableNotes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS NOTES");

        onCreate(db);
    }
}
