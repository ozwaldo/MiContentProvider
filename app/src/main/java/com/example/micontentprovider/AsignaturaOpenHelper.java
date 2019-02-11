package com.example.micontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.zip.CheckedOutputStream;

public class AsignaturaOpenHelper extends SQLiteOpenHelper {

    public static final String TAG_LOG = AsignaturaOpenHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase escrituraDB;
    SQLiteDatabase lecturaDB;

    ContentValues values = new ContentValues();

    public static final String ASIGNATURAS_TABLE_CREATE =
            "CREATE TABLE " + Contract.Asignaturas.ASIGNATURA_TABLE_ + "("
            + Contract.Asignaturas.ID + "INTEGER PRIMARY KEY, " +
            Contract.Asignaturas.NOMBRE + "TEXT);";

    public AsignaturaOpenHelper(Context context) {
        super(context, Contract.DATABASE_NOMBRE, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ASIGNATURAS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG_LOG, "Actualizar tabla de version " + oldVersion +
                "a la version " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + Contract.Asignaturas.ASIGNATURA_TABLE_);
        onCreate(db);
    }
}
