package com.trixx.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
private EditText nombre;
private Button mensaje, modificar;
private String mNombres;
private Intent mIntent;
private int id;
private static int CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nombre = (EditText) findViewById(R.id.txtNombre);
        mensaje = (Button) findViewById(R.id.btnMensaje);
        modificar = (Button) findViewById(R.id.btnModificar);
        mNombres = getIntent().getStringExtra("nombre");
        id = getIntent().getIntExtra("id", 0);

        mensaje.setOnClickListener(this);
        modificar.setOnClickListener(this);

        nombre.setText(mNombres);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMensaje:
                nombre.setText("Hola " + nombre.getText());
                break;

            case R.id.btnModificar:
                Toast.makeText(Main2Activity.this, "El nombre se cambio por " + nombre.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mIntent = new Intent(Main2Activity.this, MainActivity.class);
            mIntent.putExtra("nombre", nombre.getText().toString());
            mIntent.putExtra("id", id);
            setResult(RESULT_OK, mIntent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}