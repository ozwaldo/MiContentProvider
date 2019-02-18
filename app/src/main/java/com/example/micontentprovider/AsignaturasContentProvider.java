package com.example.micontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class AsignaturasContentProvider extends ContentProvider {

    public static final String LOG_TAG =
            AsignaturasContentProvider.class.getSimpleName();

    public String[] datos;

    private static UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    // ADD
    private AsignaturaOpenHelper db;

    private static final int URI_ALL_ITEMS_CODE = 10;
    private static final int URI_ONE_ITEM_CODE = 20;
    private static final int URI_COUNT_CODE = 30;


    // --

    @Override
    public boolean onCreate() {

        // ADD
        db = new AsignaturaOpenHelper(getContext());
        initUriMatcher();
        // --
        /*Context context = getContext();
        datos = context.getResources().
                getStringArray(R.array.asignaturas);*/

        return true;
    }

    private void initUriMatcher(){
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH + "/#",
                URI_ONE_ITEM_CODE);
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH + "/" + Contract.COUNT,
                URI_COUNT_CODE);
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH,
                URI_ALL_ITEMS_CODE);

        /*uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH + "/#", 1);
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH, 0);*/
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //int id = 1;

        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case URI_ALL_ITEMS_CODE:
                cursor = db.query(Contract.ALL_ITEMS);
                Log.d(LOG_TAG, "Obtener todos: " + cursor);
                // id = Contract.ALL_ITEMS;
                /*if (selection != null) {
                    id = Integer.parseInt(selectionArgs[0]);
                }*/
                break;
            case URI_ONE_ITEM_CODE:
                cursor = db.query(Integer.parseInt(uri.getLastPathSegment()));
                Log.d(LOG_TAG, "Obtener uno: " + cursor);
                //id = Integer.parseInt(uri.getLastPathSegment());
                break;
            case URI_COUNT_CODE:
                cursor = db.count();
                Log.d(LOG_TAG, "Obtener el numero: " + cursor);
                break;
            case UriMatcher.NO_MATCH:
                Log.d(LOG_TAG, "URI no reconocida: " + uri);
                //id = -1;
                break;
        }

        /*MatrixCursor cursor = new MatrixCursor(
                new String[]{Contract.CONTENT_PATH});
        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < datos.length; i++) {
                String asignatura = datos[i];
                cursor.addRow(new Object[]{asignatura});
            }
        } else if (id >=0 ){
            String asignatura = datos[id];
            cursor.addRow(new Object[]{asignatura});
        }*/

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case URI_ALL_ITEMS_CODE:
                return Contract.MULTIPLE_RECORDS_MIME_TYPE;
            case URI_ONE_ITEM_CODE:
                return Contract.SINGLE_RECORD_MIME_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        long id = db.insert(values);
        return Uri.parse(Contract.CONTENT_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "ELIMINAR: " + selectionArgs[0]);
        return db.delete(Integer.parseInt(selectionArgs[0]));
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection,
                       String[] selectionArgs) {
        return db.update(Integer.parseInt(selectionArgs[0]),values.getAsString("nombre"));
    }
}
