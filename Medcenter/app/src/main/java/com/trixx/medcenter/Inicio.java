package com.trixx.medcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

public class Inicio extends AppCompatActivity implements Register.RegisterListener{
private DatabaseReference databaseReference;//BD reference attribute
private FirebaseUser firebaseUser;//user attribute
private StorageReference storageReference;// storage reference for photo profile
private Users users;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    users.getUser(firebaseUser.getUid());//open fragment of profilurn true;
                case R.id.navigation_dates://view all dates of the user
                    return true;
                case R.id.navigation_logout: //logout user

                    AuthUI.getInstance()
                            .signOut(Inicio.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(Inicio.this, LoginActivity.class));
                                    finish();
                                }
                            });
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        users = new Users(databaseReference, firebaseUser, storageReference);
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {// check if the user is in database
                Boolean flag = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String id = ds.child("id").getValue(String.class);

                    if (id.equals(firebaseUser.getUid())) {// if the user is in database
                        flag = true;
                    }
                }
                if (flag.equals(false)){ //if not
                    //Open registration dialog
                    Register register = new Register();
                    register.show(getSupportFragmentManager(), "Registro");
                }
                else {
                    Profile profile = new Profile();
                    profile.setArguments(users.getUser(firebaseUser.getUid()));
                    //open profile
                    getSupportFragmentManager().beginTransaction().replace(R.id.sw, profile, null).commit();//open fragment
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startActivity(new Intent(Inicio.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void applyLogin(String name, String id, String lasts, String born, String address, String auth, String type, String esp, Uri photo) {
        if (id == null){//if the user cancel the register
            AuthUI.getInstance()
                    .delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //return to login activity
                            startActivity(new Intent(Inicio.this, LoginActivity.class));
                            finish();
                        }
                    });
        }
        else {//if the user wants to registrate
            //profile data
            users.setUser(name, id, lasts, born, address, auth, type, esp, photo);
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        //return super.onCreatePanelMenu(featureId, menu);
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//get the buttons event of menu
        return super.onOptionsItemSelected(item);
    }
}