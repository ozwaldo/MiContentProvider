package com.example.micontentprovider;

import android.net.Uri;

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

    //https://developer.android.com/guide/topics/providers/content-provider-creating.html#MIMETypes
    static final String SINGLE_RECORD_MIME_TYPE = "";
    static final String MULTIPLE_RECORDS_MIME_TYPE = "";
}