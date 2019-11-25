package com.example.p2mvc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // CONTROLADOR
    // Declaración de variables miembro
    private Button mNextButton, mPreviousButton;
    private TextView mStudentTextView;
    // Declaracion de constantes
    private static final String TAG = "etiqueta";
    private static final String KEY_INDEX = "indice";
    // Creacion de arreglo de objetos de la clase Student
    // Conexion del MODELO con CONTROLADOR
    Student[]mStudents = new Student[]{
            new Student(111, "Octavio", 100),
            new Student(222, "Juan", 82),
            new Student(333, "Pedro", 70)
    };
    //Indice del arreglo
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inflater widgets
        mNextButton = (Button) findViewById(R.id.next_button);
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mStudentTextView = (TextView) findViewById(R.id.student_textview);
        //evento onClick
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mCurrentIndex == mStudents.length - 1) {
                    mCurrentIndex = 0;
                }
                else {
                    mCurrentIndex++;
                }*/
                mCurrentIndex = (mCurrentIndex + 1) % (mStudents.length);
                updateStudent();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mCurrentIndex <= 0) {
                    mCurrentIndex = mStudents.length - 1;
                }
                else {
                    mCurrentIndex--;
                }*/
                mCurrentIndex = (mCurrentIndex + mStudents.length - 1) % (mStudents.length);
                updateStudent();
            }
        });
        //Establecer el indice con el valor almacenado en la constante
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        updateStudent();
        Log.d(TAG,"onCreate() llamado");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() llamado");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.d(TAG, "onSaveInstanceState() llamado");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() llamado");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() llamado");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() llamado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() llamado");
    }

    private void updateStudent() {
        mStudentTextView.setText(
                mStudents[mCurrentIndex].getNoControl() + "\n" +
                        mStudents[mCurrentIndex].getName() + "\n" +
                        mStudents[mCurrentIndex].getScore() + "\n"
        );
    }
}