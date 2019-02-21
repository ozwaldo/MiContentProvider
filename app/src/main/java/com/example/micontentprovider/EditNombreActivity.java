package com.example.micontentprovider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditNombreActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditNombreActivity.class.getSimpleName();

    public static final String EXTRA_REPLY =
            "com.example.micontentprovider.REPLY";

    private static final int NO_ID = -99;
    private static final String NO_NOMBRE = "";

    private EditText tvEditNombre;

    int id = MainActivity.NOMBRE_ADD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nombre);

        tvEditNombre = (EditText) findViewById(R.id.tv_edit_nombre);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int id = extras.getInt(AsignaturaAdapter.EXTRA_ID, NO_ID);
            String nombre = extras.getString(AsignaturaAdapter.EXTRA_NOMBRE);
            if (id != NO_ID && !nombre.equals(NO_NOMBRE)) {
                this.id = id;
                tvEditNombre.setText(nombre);
            }
        }
    }

    public void onClickActualizar(View view) {
        String nombre = ((EditText)
                findViewById(R.id.tv_edit_nombre)).getText().toString();

        Intent intent =  new Intent();
        intent.putExtra(EXTRA_REPLY, nombre);
        intent.putExtra(AsignaturaAdapter.EXTRA_ID, id);
        setResult(RESULT_OK, intent);
        finish();


    }
}
