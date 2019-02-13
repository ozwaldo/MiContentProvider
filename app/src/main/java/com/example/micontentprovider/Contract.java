package com.example.micontentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    private Contract(){}

    public static final String AUTHORITY =
            "com.example.micontentprovider.provider";

    public static final String CONTENT_PATH = "asignaturas";

    // content://com.example.micontentprovider.provider/asignaturas
    public static final Uri CONTENT_URI =
            Uri.parse("content://"+AUTHORITY+"/"+CONTENT_PATH);


    static final int ALL_ITEMS  = -2;
    static final String ASIGNATURA_ID = "id";


    public static final String COUNT = "count";

    // content://com.example.micontentprovider.provider/asignaturas/#
    public static final Uri ROW_COUNT_URI =
            Uri.parse("content://" + AUTHORITY+"/"+CONTENT_PATH+"/"+COUNT);


    //https://developer.android.com/guide/topics/providers/content-provider-creating.html#MIMETypes
    static final String SINGLE_RECORD_MIME_TYPE =
            "vnd.android.cursor.item/vnd.com.example.provider.asignaturas";
    static final String MULTIPLE_RECORDS_MIME_TYPE =
            "vnd.android.cursor.dir/vnd.com.example.provider.asignaturas";

    public static final String DATABASE_NOMBRE = "asignaturas";

    public static abstract class Asignaturas implements BaseColumns{

        public static final String ASIGNATURAS_TABLA = "ASIGNATURAS_NOMBRES";

        public static final String ID = "_id";
        public static final String NOMBRE = "nombre";
    }
}
