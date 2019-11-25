package com.trixx.cittme.ui.logout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.trixx.cittme.Login;
import com.trixx.cittme.R;
import java.util.Objects;

public class Logout extends AppCompatDialogFragment {
private logoutListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_logout, null);

        builder.setView(view)
                .setTitle("Salir")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.logout(false);
                    }
                })
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.logout(true);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    public interface logoutListener {
        void logout(boolean lg);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (logoutListener) context;
        }
        catch (ClassCastException e){

        }
    }
}