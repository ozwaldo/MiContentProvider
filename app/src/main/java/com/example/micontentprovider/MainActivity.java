package com.example.micontentprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_MAIN = MainActivity.class.getSimpleName();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClickBotonoes(View view) {
        switch (view.getId()) {
            case R.id.btn_mostrar_primera:
                textView.append("\n Boton1.");
                break;
            case R.id.btn_mostrar_todos:
                textView.append("\n Boton2.");
        }
    }
}
