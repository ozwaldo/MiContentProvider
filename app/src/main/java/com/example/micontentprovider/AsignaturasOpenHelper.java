package com.example.micontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.micontentprovider.Contract.*;

public class AsignaturasOpenHelper extends SQLiteOpenHelper {

    public static final String TAG_LOG =
            AsignaturasOpenHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;

    public static final String ASIGNATURAS_CREATE_TABLE =
            "CREATE TABLE " + Asignaturas.ASIGNATURAS_TABLA + "(" +
            Asignaturas.ID + " INTEGER PRIMARY KEY, " +
            Asignaturas.NOMBRE + " TEXT);";

    public AsignaturasOpenHelper(Context context) {
        super(context, Contract.DATABASE_NOMBRE, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ASIGNATURAS_CREATE_TABLE);
        llenarTabla(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG_LOG, "Actualizar Base de dastos de la version " +
                oldVersion + " a la version " + newVersion + ".");

        db.execSQL("DROP TABLE IF EXISTS " + Asignaturas.ASIGNATURAS_TABLA);
        onCreate(db);
    }

    public void llenarTabla(SQLiteDatabase db) {
        String[] datos = {"Programación Móvil II", "Taller de SO", "Programación Web",
                            "Tópicos selectos de AM"};
        ContentValues values = new ContentValues();
        for (int i = 0; i < datos.length; i++) {
            values.put(Asignaturas.NOMBRE, datos[i]);
            db.insert(Asignaturas.ASIGNATURAS_TABLA, null, values);
        }
    }


}
