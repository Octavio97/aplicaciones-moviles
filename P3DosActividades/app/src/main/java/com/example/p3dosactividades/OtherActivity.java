package com.example.p3dosactividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OtherActivity extends AppCompatActivity {
    private TextView mMessageTextView, mNameTextView;
    private static final String EXTRA_NAME_SHOWN = "name";
    private static final String SEND_MESSAGE = "mensaje";
    private static final String NAME = "Octavio";
    private Button mShowButton;

    //Asignacion del valor de la variable message a la constante
    //SEND_MESSAGE static crea metodos y variables d ela clase y esto evita que  se tenga que crear un objeto
    public static Intent newIntent(Context activity, String message) {
             Intent i = new Intent(activity, OtherActivity.class);
             i.putExtra(SEND_MESSAGE, message);
             return i;
    }

    public static String wasNameShown(Intent data) {
        return data.getStringExtra(EXTRA_NAME_SHOWN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        mMessageTextView = (TextView) findViewById(R.id.message_textview);
        mNameTextView = (TextView) findViewById(R.id.name_textView);
        mShowButton = (Button) findViewById(R.id.show_button);

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getIntent().getStringExtra(SEND_MESSAGE);
                mMessageTextView.setText(message);
                mNameTextView.setText(NAME);
                sendBackName(NAME);
            }
        });
    }
    //Recibe como parametro el nombre del estudiante
    private void sendBackName(String name) {
        Intent data = new Intent();
        //Se le asigna el valor de la variable name en la constante
        data.putExtra(EXTRA_NAME_SHOWN, name);
        setResult(RESULT_OK, data);
    }
}