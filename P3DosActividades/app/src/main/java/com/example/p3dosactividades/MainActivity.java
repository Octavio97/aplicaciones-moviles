package com.example.p3dosactividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NAME = 0;
    private static final String TAG = "Etiqueta";
    //Declaracion variables miembro
    private TextView mHelloTextview;
    private Button mOtherActivityButton;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inflar widgets
        mHelloTextview = (TextView) findViewById(R.id.hello_textview);
        mOtherActivityButton = (Button) findViewById(R.id.other_activity_button);
        //Evento onClick
        mOtherActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mHelloTextview.getText().toString();
                Intent intent = OtherActivity.newIntent(MainActivity.this, message);
                startActivityIfNeeded(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_NAME) {
            if (data == null) {
                return;
            }
            //Assignacion del valor del al variable name de la clase  OtherActivity a la variable mName de esta clase
            mName = OtherActivity.wasNameShown(data);
            mHelloTextview.setText(mName);
        }
        Log.d(TAG, "onActivityResult() llamado");
    }
}