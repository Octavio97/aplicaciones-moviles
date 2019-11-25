package com.trixx.p11firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText edNom, edAp, edCor, edPas;
    private ListView listV_personas;
    //List y Array
    private List<Person> listPerson = new ArrayList<Person>();
    ArrayAdapter<Person> arrayAdapterPersona;
    //Declaracion de la clase selecter
    Person personSelected;

    //Creacion e variables para manipulacion de Firebase
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabaseReference.getRef();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNom = findViewById(R.id.txt_nombrePersona);
        edAp = findViewById(R.id.txt_apellidoPersona);
        edCor = findViewById(R.id.txt_correoPersona);
        edPas = findViewById(R.id.txt_passwordPersona);

        listV_personas=findViewById(R.id.lv_datosPersonas);
        initializeFirebase();
        listData();
        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personSelected = (Person) parent.getItemAtPosition(position);
                edNom.setText(personSelected.getNombre());
                edAp.setText(personSelected.getApellido());
                edCor.setText(personSelected.getCorreo());
                edPas.setText(personSelected.getPassword());
            }
        });
    }

    private void listData() {
        mDatabaseReference.child("Person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Person p = objSnapshot.getValue(Person.class);
                    arrayAdapterPersona = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_personas.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombre = edNom.getText().toString();
        String apellido = edAp.getText().toString();
        String correo = edCor.getText().toString();
        String password = edPas.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || password.equals("")){
                   validation();
                }
                else {
                    Person p = new Person();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreo(correo);
                    p.setPassword(password);
                    mDatabaseReference.child("Person").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.icon_delete:
                Person pe = new Person();
                pe.setUid(personSelected.getUid());
                mDatabaseReference.child("Person").child(pe.getUid()).removeValue();
                clearText();
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_save:
                Person p = new Person();
                p.setUid(personSelected.getUid());
                p.setNombre(edNom.getText().toString().trim());
                p.setApellido(edAp.getText().toString().trim());
                p.setCorreo(edCor.getText().toString().trim());
                p.setPassword(edPas.getText().toString().trim());
                mDatabaseReference.child("Person").child(p.getUid()).setValue(p);
                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearText() {
        edNom.setText("");
        edAp.setText("");
        edCor.setText("");
        edPas.setText("");
    }

    private void validation() {
        String nombre = edNom.getText().toString();
        String apellido = edAp.getText().toString();
        String correo = edCor.getText().toString();
        String password = edPas.getText().toString();

        if (nombre.equals("")){
            edNom.setError("Requerido");
        }
        else if (apellido.equals("")) {
            edAp.setError("Requerido");
        }
        else if (correo.equals("")) {
            edCor.setError("Requerido");
        }
        else if (password.equals("")) {
            edPas.setError("Requerido");
        }
    }
}