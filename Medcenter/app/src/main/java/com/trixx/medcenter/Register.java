package com.trixx.medcenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatDialogFragment {
private EditText name, lasts, born, address;
private Spinner type, especiality;
private Button photo;
private FirebaseUser user;
private DatabaseReference reference;
private RegisterListener listener;
private Uri uri;
private static final int GALLERY_INTENT = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_register, null);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        name = view.findViewById(R.id.lblName);
        lasts = view.findViewById(R.id.lblLasts);
        born = view.findViewById(R.id.txtDate);
        address = view.findViewById(R.id.txtAddress);
        type = view.findViewById(R.id.txtType);
        photo = view.findViewById(R.id.btnPhoto);
        especiality = view.findViewById(R.id.txtEsp);
        especiality.setVisibility(View.INVISIBLE);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { //show especiality button
                if (type.getSelectedItemPosition() == 1){//if the user is a doctor
                    especiality.setVisibility(View.VISIBLE);
                }
                else {//if the user is a pacient
                    especiality.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setView(view)
                .setTitle("Registro")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {//cancel registration
                        listener.applyLogin(null, null, null, null, null, null, null, null, null);
                    }
                })
                .setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {//if commit
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tipo = (String) type.getSelectedItem();//get type of user
                        String esp = (String) especiality.getSelectedItem();//get especiality
                        if (tipo != "Doctor"){//if it is a pacient
                            esp = " ";
                        }
                        if (type.getSelectedItemPosition() == 0 || name.getText() == null || lasts.getText() == null || address.getText() == null || born.getText() == null || uri.equals(null)){
                            Register register = new Register();
                            register.show(getFragmentManager(), "Registro");
                        }
                        else {
                            if (user.getPhoneNumber() == null){//if the auth was a email address
                                listener.applyLogin(name.getText().toString(), user.getUid(), lasts.getText().toString(), born.getText().toString(), address.getText().toString(), user.getEmail(), tipo, esp, uri);
                            }
                            else if (user.getEmail() == null){//if the auth was a phone number
                                listener.applyLogin(name.getText().toString(), user.getUid(), lasts.getText().toString(), born.getText().toString(), address.getText().toString(), user.getPhoneNumber(), tipo, esp, uri);
                            }
                        }
                    }
                });

        photo.setOnClickListener(new View.OnClickListener() {//load profile photo
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), GALLERY_INTENT);
            }
        });

        return builder.create();
    }

    public interface RegisterListener{
        void applyLogin(String name, String id, String lasts, String born, String address, String auth, String type, String esp, Uri photo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RegisterListener) context;
        }
        catch (ClassCastException e){

        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        listener.applyLogin(null, null, null, null, null, null, null, null, null);
    }
}
