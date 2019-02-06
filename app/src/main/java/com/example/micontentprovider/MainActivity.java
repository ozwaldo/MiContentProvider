package com.example.micontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = MainActivity.class.getSimpleName();
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
    }


    public void onClickDisplayEntries(View view) {
        String queryUri = Contract.CONTENT_URI.toString();

        String[] projection = new String[]{Contract.CONTENT_PATH};

        String selectionClause;

        String selectionArgs[];

        String selectionOrder = null;

        Log.d(TAG_MAIN, queryUri.toString());
        switch (view.getId()) {
            case R.id.btn_mostrar_primero:
                //Log.d(TAG_MAIN, "Botón: " + R.id.btn_mostrar_primero);
                selectionClause = Contract.ASIGNATURA_ID + " = ?";
                selectionArgs = new String[]{"0"};
                break;
            case R.id.btn_mostrar_todo:
                //Log.d(TAG_MAIN, "Botón: " + R.id.btn_mostrar_todo);
                selectionClause = null;
                selectionArgs = null;
                break;
            default:
                // Log.d(TAG_MAIN, "Error");
                selectionArgs = null;
                selectionClause = null;
        }

        Cursor cursor = getContentResolver().query(
                Uri.parse(queryUri),
                projection,
                selectionClause,
                selectionArgs,
                selectionOrder
        );

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                do {
                    String asignatura = cursor.getString(columnIndex);
                    mTextView.append(asignatura + "\n");
                }while (cursor.moveToNext());
            } else {
                Log.d(TAG_MAIN, "onClickDisplayEntries " + " No existen datos.");
                mTextView.append("No existen datos." + "\n");
            }
            cursor.close();
        } else {
            Log.d(TAG_MAIN, "onClickDisplayEntries " + "Cursor vacío");
            mTextView.append("Cursor vacío." + "\n");
        }
        //mTextView.append("Asignaturas: \n");
    }
}
