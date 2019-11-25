package com.example.p1holamundo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnHola, btnMundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inflate widgets
        btnHola = (Button) findViewById(R.id.btnHello);
        btnMundo = (Button) findViewById(R.id.btnWorld);

        //events
        btnHola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.hello, Toast.LENGTH_SHORT).show();
            }
        });

        btnMundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.world, Toast.LENGTH_SHORT).show();
            }
        });
    }
}