package com.example.micontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class AsignaturasContentProvider extends ContentProvider {

    public static final String LOG_TAG =
            AsignaturasContentProvider.class.getSimpleName();

    public String[] datos;

    private static UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        initUriMatcher();
        Context context = getContext();
        datos = context.getResources().
                getStringArray(R.array.asignaturas);

        return true;
    }

    private void initUriMatcher(){
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH + "/#", 1);
        uriMatcher.addURI(
                Contract.AUTHORITY,Contract.CONTENT_PATH, 0);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int id = 1;

        switch (uriMatcher.match(uri)){
            case 0:
                id = Contract.ALL_ITEMS;
                if (selection != null) {
                    id = Integer.parseInt(selectionArgs[0]);
                }
                break;
            case 1:
                id = Integer.parseInt(uri.getLastPathSegment());
                break;
            case UriMatcher.NO_MATCH:
                id = -1;
        }

        MatrixCursor cursor = new MatrixCursor(
                new String[]{Contract.CONTENT_PATH});

        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < datos.length; i++) {
                String asignatura = datos[i];
                cursor.addRow(new Object[]{asignatura});
            }
        } else if (id >=0 ){
            String asignatura = datos[id];
            cursor.addRow(new Object[]{asignatura});
        }


        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case 0:
                return Contract.MULTIPLE_RECORDS_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection,
                       String[] selectionArgs) {
        return 0;
    }
}
