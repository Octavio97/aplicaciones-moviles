package com.trixx.medcenter;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment{
private TextView nombres, apellidos, nacimiento, direccion, autenticacion, especialidad;
private ImageView photo;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment);
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

        //load profile
        if (getArguments() != null){
            nombres.setText("Nombre: " + getArguments().getString("nombre") + "\n");
            apellidos.setText("Apellidos: " + getArguments().getString("apellidos") + "\n");
            nacimiento.setText("Fecha de nacimiento: " + getArguments().getString("fecha_nac") + "\n");
            direccion.setText("Dirección:" + "\n" + getArguments().getString("direccion") + "\n");
            autenticacion.setText("Autenticación:" + "\n" + getArguments().getString("auth") + "\n");

            if (!getArguments().getString("url").equals(null)){
                Picasso.get().load(getArguments().getString("url")).into(photo);
            }

            if (getArguments().getString("usuario").equals("Doctor")){
                especialidad.setText("Especialidad: " + "\n" + getArguments().getString("especialidad"));
                especialidad.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }
}