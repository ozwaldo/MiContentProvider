package com.example.micontentprovider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNombreAcitivity extends AppCompatActivity {

    private static final String LOG_TAG = EditNombreAcitivity.class.getSimpleName();

    public static final String EXTRA_RESTPUESTA = "com.example.micontentprovider.RESPUESTA";

    EditText etEditNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nombre_acitivity);

        etEditNombre = (EditText) findViewById(R.id.tv_edit_nombre);

        Intent intent = getIntent();

        etEditNombre.setText(
                intent.getStringExtra(AsignaturaAdapter.EXTRA_NOMBRE));

    }

    public void onClickActualizar(View view) {

    }
}
