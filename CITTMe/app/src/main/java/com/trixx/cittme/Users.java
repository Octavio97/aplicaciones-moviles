package com.trixx.cittme;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class Users {
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;

    public Users () {
        //set firebase objects
        this.database = FirebaseDatabase.getInstance();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    /*--------getters and setters----------*/
    public FirebaseDatabase getDBR() {
        return database;
    }

    public FirebaseUser getFU() {
        return firebaseUser;
    }

    public StorageReference getSR() {
        return storageReference;
    }

    //get data of user profile or date information to fill textviews
    public void getData(String table, final String dato, final TextView textView) {
        database.getReference().child(table).child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView.setText(dato + ":\n" + Objects.requireNonNull(dataSnapshot.child(dato).getValue()).toString() + "\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //set data of profile user or date information
    public void setData(final String data, final String input, final String table) {
        database.getReference().child(table).child(firebaseUser.getUid()).child(data).setValue(input);
    }

    //set the photo profile
    public void setPhoto(Uri photo, final String table, final String data) {
        if (photo == null){//if the user doesn't put a photo
            database.getReference().child("Users").child(firebaseUser.getUid()).child("url").setValue("null");
        }
        else {
            //add photo profile
            storageReference.child(firebaseUser.getUid()).child("profile").putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //get url from the photo
                    storageReference.child(firebaseUser.getUid()).child("profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //catch url
                            HashMap<String, String> url = new HashMap<>();
                            url.put("url" , String.valueOf(uri));
                            //put url in database
                            database.getReference().child(table).child(firebaseUser.getUid()).child(data).setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("URL", "URL GUARDADO");
                                }
                            });
                        }
                    });
                }
            });//no entro
        }
    }

    //get profile photo
    public void getPhoto(final ImageView imageView) {
        database.getReference().child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = Objects.requireNonNull(dataSnapshot.child("url").getValue()).toString();

                if (!url.equals("null")){//if there is a photo in database
                    Picasso.get().load(url).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface dataListener {
        void getData(String table, final String datos);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}