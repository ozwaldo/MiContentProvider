package com.example.micontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import static java.lang.Integer.parseInt;

public class AsignaturasContentProvider extends ContentProvider {

    public static final String LOG_TAG = AsignaturasContentProvider.class.getSimpleName();
    public String[] mDatos;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        initUriMatcher();
        Context context = getContext();
        mDatos = context.getResources().getStringArray(R.array.asignaturas);
        return true;
    }

    private void initUriMatcher(){
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int id = 1;

        switch (sUriMatcher.match(uri)) {
            case 0:
                id = Contract.ALL_ITEMS;
                if (selection != null) {
                    id = parseInt(selectionArgs[0]);
                }
                break;
            case 1:
                id = parseInt(uri.getLastPathSegment());
                break;
            case UriMatcher.NO_MATCH:
                Log.d(LOG_TAG, "No existen resultados para la URI");
                id = -1;
                break;
            default:
                Log.d(LOG_TAG, "URI invalida.");
                id = -1;
        }

        Log.d(LOG_TAG, "query: " + id);


        return populateCursor(id);
    }

    private Cursor populateCursor(int id) {
        MatrixCursor cursor = new MatrixCursor(new String[]{Contract.CONTENT_PATH});

        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < mDatos.length; i++) {
                String asignaturas = mDatos[i];
                cursor.addRow(new Object[]{asignaturas});
            }
        } else if (id >= 0) {
            String asignaturas = mDatos[id];
            cursor.addRow(new Object[]{asignaturas});
        }
        return cursor;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 0:
                return Contract.MULTIPLE_RECORDS_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(LOG_TAG, "No implementado: insert uri: " + uri.toString());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection,String[] selectionArgs) {
        Log.e(LOG_TAG, "No implementado: delete uri: " + uri.toString());

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(LOG_TAG, "No implementado: update uri: " + uri.toString());

        return 0;
    }
}
