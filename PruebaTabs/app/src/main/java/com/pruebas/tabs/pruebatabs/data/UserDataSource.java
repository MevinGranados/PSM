package com.pruebas.tabs.pruebatabs.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pruebas.tabs.pruebatabs.Model.User;

/**
 * Created by Kevin on 07/05/2016.
 */
public class UserDataSource {
    private static final String TABLE_NAME = "MYUSER";

    private SqliteDB mDBHelper;
    private SQLiteDatabase mDatabase;

    public UserDataSource(Context context) {
        mDBHelper = new SqliteDB(context);
    }

    private void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    private void close() {
        if (mDatabase.isOpen()) {
            mDatabase.close();
        }
    }

    public void addNote(User user) {
        open();
        ContentValues values = new ContentValues();

        values.put("IDMYUSER", user.getMid());
        values.put("NAME", user.getName());
        values.put("PASSWORD", user.getPassword());
        values.put("USERNAME", user.getUsername());

        mDatabase.insert(TABLE_NAME, null, values);
        close();
    }

    public void updateNote(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put("IDMYUSER", user.getMid());
        values.put("NAME", user.getName());
        values.put("PASSWORD", user.getPassword());
        values.put("USERNAME", user.getUsername());

        String where = "ID = " + 1;
        mDatabase.update(TABLE_NAME, values, where, null);

        close();
    }

    public User getUser() {
        User user = null;

        open();

        String where = "ID = " + 1;
        Cursor cursor = mDatabase.query(TABLE_NAME, null, where, null, null, null, null, null);

        // Movemos el cursor al primer elemetro de la seleccion
        // (query)
        if (cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex("IDMYUSER"));
            String Name = cursor.getString(cursor.getColumnIndex("NAME"));
            String Pass = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            String Username = cursor.getString(cursor.getColumnIndex("USERNAME"));

            user = new User(id, Username, Pass,Name);

            // Cerramos el cursor
            cursor.close();
        }

        close();

        return user;
    }
}
