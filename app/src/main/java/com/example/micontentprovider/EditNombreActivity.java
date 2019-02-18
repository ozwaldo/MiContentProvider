package com.example.micontentprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditNombreActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY =
            "com.example.micontentprovider.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nombre);
    }
}
