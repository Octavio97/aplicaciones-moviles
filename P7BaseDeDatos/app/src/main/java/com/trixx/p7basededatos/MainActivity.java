package com.trixx.p7basededatos;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    //Declaracion de varables
    private GridView mGridView;
    public ArrayList<String> ArrayOfName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, ArrayOfName));
        //Creamos un objeto de la clase DatabaseHelper
        DatabaseHelper db = new DatabaseHelper(this);
        //CRUD
        //Creamos objetos de la clase contact
        Contact juan = new Contact(1, "Juan", "664-123-4586");
        Contact maria = new Contact(2, "Maria", "664-979-3456");
        Contact luis = new Contact(3, "Luis", "664-875-2549");
        Contact ana = new Contact(4, "Ana", "664-325-7456");
        //Create
        db.addContact(juan);
        db.addContact(maria);
        db.addContact(luis);
        db.addContact(ana);
        //Delete
        db.deleteContact(luis);
        //Obtener todos los contactos
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String cont = cn.getName() + "\n" + cn.getPhoneNumber();
            //Escribir contactos a cont
            ArrayOfName.add(cont);
        }
        //Regresa la cuenta de contactos
        ArrayOfName.add("Cuenta: " + db.getContactsCount());
        //Update
        db.updateContacts(2, "Pedro", "646-123-7623");
        Contact consulta = db.getContact(2);
        ArrayOfName.add(consulta.getName() + " " +consulta.getPhoneNumber());
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
    }
}