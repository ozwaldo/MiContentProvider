package com.example.micontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.zip.CheckedOutputStream;

import static com.example.micontentprovider.Contract.*;

public class AsignaturaOpenHelper extends SQLiteOpenHelper {

    public static final String TAG_LOG = AsignaturaOpenHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase escrituraDB;
    SQLiteDatabase lecturaDB;

    ContentValues values = new ContentValues();

    public static final String ASIGNATURAS_TABLE_CREATE =
            "CREATE TABLE " + Asignaturas.ASIGNATURA_TABLE_ + "("
            + Asignaturas.ID + " INTEGER PRIMARY KEY, " +
            Asignaturas.NOMBRE + " TEXT);";

    public AsignaturaOpenHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ASIGNATURAS_TABLE_CREATE);
        llenarTabla(db);

    }

    public void llenarTabla(SQLiteDatabase db) {
        String[] datos = {"Programación Móvil II", "Taller de SO", "Programación Web",
                "Tópicos selectos de AM"};
        ContentValues values = new ContentValues();
        for (int i = 0; i < datos.length; i++) {
            values.put(Asignaturas.NOMBRE, datos[i]);
            db.insert(Asignaturas.ASIGNATURA_TABLE_, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG_LOG, "Actualizar tabla de version " + oldVersion +
                "a la version " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + Asignaturas.ASIGNATURA_TABLE_);
        onCreate(db);
    }

    public Cursor query(int posicion) {
        String query;
        if (posicion != ALL_ITEMS) {
            posicion++;
            query = "SELECT " + Asignaturas.ID + "," + Asignaturas.NOMBRE +
                    " FROM " + Asignaturas.ASIGNATURA_TABLE_ +
                    " WHERE " + Asignaturas.ID + "=" + posicion + ";";
        } else {
            query = "SELECT * FROM " + Asignaturas.ASIGNATURA_TABLE_ +
                    " ORDER BY " + Asignaturas.NOMBRE + " ASC;";
        }

        Cursor cursor = null;

        try {
            if (lecturaDB == null) {
                lecturaDB = this.getReadableDatabase();
            }
            cursor = lecturaDB.rawQuery(query, null);
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error en consulta: " + e);
        } finally {
            return cursor;
        }
    }

    public Cursor count() {
        Log.d(TAG_LOG, "entra al count");
        MatrixCursor cursor = new MatrixCursor(new String[]{CONTENT_PATH});
        try {
            if (lecturaDB == null){
                lecturaDB = getReadableDatabase();
            }
            int count = (int) DatabaseUtils.
                    queryNumEntries(lecturaDB, Asignaturas.ASIGNATURA_TABLE_);
            cursor.addRow(new Object[]{count});

        } catch (Exception e) {
            Log.d(TAG_LOG, "Error Count: " + e);
        }

        return cursor;
    }

    public long insert(ContentValues values) {
        long resultado = 0;
        try{
            if (escrituraDB == null) {
                escrituraDB = getWritableDatabase();
            }
            resultado = escrituraDB.insert(Asignaturas.ASIGNATURA_TABLE_,
                    null, values);
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error al insertar: " + e);
        }
        return resultado;
    }

    public int update(int id, String asignatura) {
        int resultado = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(Asignaturas.NOMBRE, asignatura);
            if (escrituraDB == null) {
                escrituraDB = getWritableDatabase();
            }
            resultado = escrituraDB.update(
                    Asignaturas.ASIGNATURA_TABLE_,
                    values,
                    Asignaturas.ID  + "= ?",
                    new String[]{String.valueOf(id)}
            );
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error al actualizar: " + e);
        }
        return resultado;
    }

    public int delete(int id) {
        int resultado = 0;
        try{
            if (escrituraDB == null) {
                escrituraDB = getWritableDatabase();
            }
            resultado = escrituraDB.delete(
                    Asignaturas.ASIGNATURA_TABLE_,
                    Asignaturas.ID + "=?",
                    new String[]{String.valueOf(id)}
            );
        } catch(Exception e) {
            Log.d(TAG_LOG, "Error al eliminar: " + e);
        }
        return resultado;
    }
}
