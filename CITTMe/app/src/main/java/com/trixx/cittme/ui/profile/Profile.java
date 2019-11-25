package com.trixx.cittme.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.trixx.cittme.R;
import com.trixx.cittme.Register;
import com.trixx.cittme.Users;

import java.text.ParsePosition;
import java.util.Objects;

public class Profile extends Fragment implements Users.dataListener{
    private TextView nombres, apellidos, nacimiento, direccion, autenticacion, especialidad;
    private ImageView photo;
    private  Users mUsers;
    private FirebaseDatabase dbr;
    private FirebaseUser fu;
    private StorageReference sr;

    public Profile () {
        mUsers = new Users();
        dbr = mUsers.getDBR();
        fu = mUsers.getFU();
        sr = mUsers.getSR();
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //reference objects with views
        nombres = view.findViewById(R.id.lblName);
        autenticacion =  view.findViewById(R.id.lblEP);
        apellidos = view.findViewById(R.id.lblLasts);
        nacimiento = view.findViewById(R.id.lblBorn);
        direccion = view.findViewById(R.id.lblAddress);
        especialidad = view.findViewById(R.id.lblEspecial);
        photo = view.findViewById(R.id.imgProf);
        especialidad.setVisibility(View.INVISIBLE);
        //Verify the user exist
        userData();

        return view;
    }

    @Override
    public void getData(String table, final String datos) {
        dbr.getReference().child(table).child(fu.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if the user is a doctor
                String esp = Objects.requireNonNull(dataSnapshot.child(datos).getValue()).toString();
                if (Objects.equals(esp, "Doctor")){
                    especialidad.setText("Especialidad: " + "\n" + Objects.requireNonNull(dataSnapshot.child("especialidad").getValue()).toString());//set the speciality
                    especialidad.setVisibility(View.VISIBLE);//view the textview
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //determinate if the user needs to regitrate
    public void userData() {
        dbr.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean flag = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    if (id.equals(fu.getUid())) {// if the user is in database
                        flag = true;
                    }
                }
                if (flag.equals(false)){ //if isn´t registrated
                    //Open registration dialog
                    Register register = new Register();
                    register.show(getFragmentManager(), "Registro");
                }
                else if(flag.equals(true)){//if the user exist
                    //set the data in the fragment
                    mUsers.getData("Users", "nombre", nombres);
                    mUsers.getData("Users", "apellidos", apellidos);
                    mUsers.getData("Users", "fecha nacimiento", nacimiento);
                    mUsers.getData("Users", "direccion", direccion);
                    mUsers.getData("Users", "auth", autenticacion);
                    getData("Users", "usuario");
                    mUsers.getPhoto(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}