package com.example.micontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.micontentprovider.Contract.*;

public class AsignaturasOpenHelper extends SQLiteOpenHelper {

    public static final String TAG_LOG =
            AsignaturasOpenHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase lecturaBd;
    SQLiteDatabase escrituraBd;

    public static final String ASIGNATURAS_CREATE_TABLE =
            "CREATE TABLE " + Asignaturas.ASIGNATURAS_TABLA + "(" +
            Asignaturas.ID + " INTEGER PRIMARY KEY, " +
            Asignaturas.NOMBRE + " TEXT);";

    public AsignaturasOpenHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);

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

    public Cursor query(int posicion) {
        String query;
        if (posicion != ALL_ITEMS) {
            posicion++;
            query = "SELECT "  + Asignaturas.ID + "," + Asignaturas.NOMBRE +
                    " FROM " + Asignaturas.ASIGNATURAS_TABLA +
                    " WHERE "  + Asignaturas.ID + " = " + posicion;
        } else {
            query = "SELECT "  + Asignaturas.ID + "," + Asignaturas.NOMBRE +
                    " FROM " + Asignaturas.ASIGNATURAS_TABLA +
                    " ORDER BY "  + Asignaturas.NOMBRE + " ASC";
        }
        Cursor cursor = null;
        try{
            if (lecturaBd == null) {
                lecturaBd = getReadableDatabase();
            }
            cursor = lecturaBd.rawQuery(query,null);
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error al realiza la consulta: " + e);
        } finally {
            return  cursor;
        }

    }

    public long insert(ContentValues contentValues) {
        long resultado = 0;

        try{
            if (escrituraBd == null) {
                escrituraBd = getWritableDatabase();
            }
            resultado = escrituraBd.insert(
                    Asignaturas.ASIGNATURAS_TABLA,
                    null,
                    contentValues
            );
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error al insertar datos: " + e);
        }
        return resultado;
    }

    public int update(int id, String asingatura){
        int resultado = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(Asignaturas.NOMBRE, asingatura);
            if (escrituraBd == null) {
                escrituraBd = getWritableDatabase();
            }

            resultado = escrituraBd.update(
                    Asignaturas.ASIGNATURAS_TABLA,
                    values,
                    Asignaturas.ID + " = ?",
                    new String[]{String.valueOf(id)}
            );
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error al actualizar: "  + e);
        }
        return resultado;
    }

    public int delete(int id) {
        int resultado = 0;
        try{
            if (escrituraBd == null) {
                escrituraBd = getWritableDatabase();
            }
            resultado = escrituraBd.delete(
                    Asignaturas.ASIGNATURAS_TABLA,
                    Asignaturas.ID + " = ?",
                    new String[]{String.valueOf(id)}
            );
        }catch (Exception e) {
            Log.d(TAG_LOG, "Error al eliminar: " + e);
        }
        return resultado;
    }

    public Cursor count() {
        MatrixCursor cursor = new MatrixCursor(
                new String[]{CONTENT_PATH});
        try {
            if (lecturaBd == null) {
                lecturaBd = getReadableDatabase();
            }
            int count = (int)
                    DatabaseUtils.queryNumEntries(
                            lecturaBd,Asignaturas.ASIGNATURAS_TABLA);
            cursor.addRow(new Object[]{count});
        } catch (Exception e) {
            Log.d(TAG_LOG, "Error Count: " + e);
        }
        return cursor;
    }
}
