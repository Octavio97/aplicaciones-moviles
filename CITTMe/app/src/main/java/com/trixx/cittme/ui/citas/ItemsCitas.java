package com.trixx.cittme.ui.citas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trixx.cittme.R;
import com.trixx.cittme.Users;

import java.util.ArrayList;

public class ItemsCitas extends RecyclerView.Adapter<ItemsCitas.ViewHolderDatos> {
    ArrayList<String> lstDatos;

    public ItemsCitas(ArrayList<String> lstDatos) {
        this.lstDatos = lstDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_citas, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.getData("Citas", "");
    }

    @Override
    public int getItemCount() {
        return lstDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements Users.dataListener {
        TextView fecha, doctor;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.txt_date);
            doctor = itemView.findViewById(R.id.txt_doctor);
        }

        @Override
        public void getData(String table, String datos) {

        }
    }
}
