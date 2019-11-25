package com.trixx.medcenter;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;

public class Users extends AppCompatActivity {
    private String name, id, last, born, address, auth, type, esp;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;

    public Users(DatabaseReference databaseReference, FirebaseUser firebaseUser, StorageReference storageReference) {
        this.databaseReference = databaseReference;
        this.firebaseUser = firebaseUser;
        this.storageReference = storageReference;
    }

    //set user in DB method
    public void setUser(String name, String id, String last, String born, String address, String auth, String type, String esp, Uri photo) {
        this.name = name;
        this.id = id;
        this.last = last;
        this.born = born;
        this.address = address;
        this.auth = auth;
        this.type = type;
        this.esp = esp;

        databaseReference.child("Users").child(firebaseUser.getUid()).child("id").setValue(id);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("nombre").setValue(name);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("apellidos").setValue(last);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("fecha_nac").setValue(born);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("direccion").setValue(address);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("auth").setValue(auth);
        databaseReference.child("Users").child(firebaseUser.getUid()).child("usuario").setValue(type);

        //add photo profile
        UploadTask path = storageReference.child(firebaseUser.getUid()).child("profile").putFile(photo);

        Task<Uri> task = path.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.d("ERROR", "Error: " + task.getException().getMessage());
                }
                return storageReference.getDownloadUrl();
            }
        })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Log.d("URL_PROFILE", task.getResult().toString().substring(0, task.getResult().toString().indexOf("&token")));
                            databaseReference.child("Users").child(firebaseUser.getUid()).child("url").setValue(task.getResult().toString().substring(0, task.getResult().toString().indexOf("&token")));
                        }
                    }
                });
        //if the user is a pacient
        if (esp != " ") {
            databaseReference.child("Users").child(firebaseUser.getUid()).child("especialidad").setValue(esp);
        }
    }

    public Bundle getUser(String uid) {
        final Bundle bundle = new Bundle();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){//get all users values

                    if (Objects.equals(ds.child("id").getValue(String.class), firebaseUser.getUid())){ //get profile from the login user
                        //get and send data
                        bundle.putString("id", dataSnapshot.child(firebaseUser.getUid()).child("id").getValue(String.class));
                        bundle.putString("nombre", dataSnapshot.child(firebaseUser.getUid()).child("nombre").getValue(String.class));
                        bundle.putString("apellidos", dataSnapshot.child(firebaseUser.getUid()).child("apellidos").getValue(String.class));
                        bundle.putString("fecha_nac", dataSnapshot.child(firebaseUser.getUid()).child("fecha_nac").getValue(String.class));
                        bundle.putString("direccion", dataSnapshot.child(firebaseUser.getUid()).child("direccion").getValue(String.class));
                        bundle.putString("auth", dataSnapshot.child(firebaseUser.getUid()).child("auth").getValue(String.class));
                        bundle.putString("usuario", dataSnapshot.child(firebaseUser.getUid()).child("usuario").getValue(String.class));
                        bundle.putString("especialidad", dataSnapshot.child(firebaseUser.getUid()).child("especialidad").getValue(String.class));
                        bundle.putString("url", dataSnapshot.child(firebaseUser.getUid()).child("url").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return bundle;
    }
}