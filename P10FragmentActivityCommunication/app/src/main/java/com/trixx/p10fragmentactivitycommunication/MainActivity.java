package com.trixx.p10fragmentactivitycommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Message.OnMessageReadListener {
    //Declarar variables miembro
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Message message = new Message();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, message, null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onMessageRead(String message) {
        mTextView = findViewById(R.id.txt_display_message);
        mTextView.setText(message);
    }
}