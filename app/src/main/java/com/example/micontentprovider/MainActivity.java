package com.example.micontentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = MainActivity.class.getSimpleName();

    public static final int NOMBRE_EDIT = 99;

    private RecyclerView recyclerView;
    private AsignaturaAdapter adapter;

    //TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = (TextView) findViewById(R.id.textView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new AsignaturaAdapter(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOMBRE_EDIT) {
            if (resultCode == RESULT_OK) {
                String nombre = data.getStringExtra(
                        EditNombreAcitivity.EXTRA_RESTPUESTA);

                if (nombre.length() != 0) {
                    ContentValues values = new ContentValues();
                   values.put(Contract.Asignaturas.NOMBRE,nombre);

                   int id = data.getIntExtra(
                           AsignaturaAdapter.EXTRA_ID, 0);

                    if (id != 0) {
                        getContentResolver().update(
                                Contract.CONTENT_URI,
                                values,
                                Contract.Asignaturas.ID,
                                new String[]{String.valueOf(id)}
                        );
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No se modifico la información",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    /*public void onClickBotones(View view) {

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
    }*/
}









