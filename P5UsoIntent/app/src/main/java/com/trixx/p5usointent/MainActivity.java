package com.trixx.p5usointent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 1;
    private Button mCamera, mLlamada, mNavegador;
private static final String TAG = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = (Button) findViewById(R.id.camera_button);
        mLlamada = (Button) findViewById(R.id.call_button);
        mNavegador = (Button) findViewById(R.id.nav_button);

        mCamera.setOnClickListener(this);
        mLlamada.setOnClickListener(this);
        mNavegador.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch(v.getId()) {
                case R.id.camera_button:
                    startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                    break;

                case R.id.call_button:
                    startActivity(new Intent(Intent.ACTION_DIAL));
                    break;

                case R.id.nav_button:
                    Uri uri = Uri.parse("http://www.google.com/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    break;
            }
        }
        catch (Exception e) {
            Log.d(TAG, e.getMessage().toString());
        }
    }
}
