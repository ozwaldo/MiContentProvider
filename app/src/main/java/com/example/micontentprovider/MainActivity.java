package com.example.micontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = MainActivity.class.getSimpleName();

    public static final int ASIGNATURA_EDIT = -1;

    private RecyclerView recyclerView;


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClickBotones(View view) {

        String queryUri = Contract.CONTENT_URI.toString();

        Log.d(TAG_MAIN, queryUri);

        String[] projection = new String[]{Contract.CONTENT_PATH};

        String selectionClause;
        String selectionArgs[];

        switch (view.getId()) {
            case R.id.btn_mostrar_primera:
                // SELECT * FROM asignatura WHERE ASIGNATURA_ID = 0
                selectionClause = Contract.ASIGNATURA_ID + " = ?";
                selectionArgs = new String[]{"0"};
                //textView.append("\n Boton1.");
                break;
            case R.id.btn_mostrar_todos:
                selectionClause = null;
                selectionArgs = null;
                //textView.append("\n Boton2.");
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }

        Cursor cursor = getContentResolver().query(
                Uri.parse(queryUri),
                projection,
                selectionClause,
                selectionArgs,
                null
        );

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                do {
                    String asignatura = cursor.getString(columnIndex);
                    textView.append(asignatura + "\n");
                } while (cursor.moveToNext());
            } else {
                textView.append("No existen asignaturas.");
            }
        } else {
            textView.append("No existen Asignaturas.");
        }


    }
}









