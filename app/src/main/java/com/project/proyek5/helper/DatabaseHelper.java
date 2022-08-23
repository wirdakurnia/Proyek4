package com.project.proyek5.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.project.proyek5.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "db_contact";
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_TELP = "telp";
    private static final String CREATE_CONTACTS = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + "" +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAMA + " TEXT NOT NULL," + COLUMN_ALAMAT +
            " TEXT NOT NULL," + COLUMN_TELP + " TEXT NOT NULL)" ;
    private static final String TAG = DatabaseHelper.class.getName();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACTS);
        Log.d(TAG, "onCreate: " + CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    public long insert(Contact contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, contact.getNama());
        values.put(COLUMN_ALAMAT, contact.getAlamat());
        values.put(COLUMN_TELP, contact.getTelp());
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public boolean delete(String id){
        return getWritableDatabase().delete(TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean update(Contact contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, contact.getNama());
        values.put(COLUMN_ALAMAT, contact.getAlamat());
        values.put(COLUMN_TELP, contact.getTelp());
        return getWritableDatabase().update(TABLE_NAME, values, COLUMN_ID + "=" + contact.getId(), null) > 0;
    }
}
