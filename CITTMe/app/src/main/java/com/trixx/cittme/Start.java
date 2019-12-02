package com.trixx.cittme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.trixx.cittme.ui.citas.Citas;
import com.trixx.cittme.ui.logout.Logout;
import com.trixx.cittme.ui.profile.Profile;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Start extends AppCompatActivity implements Register.RegisterListener, Logout.logoutListener{
    private FirebaseDatabase database;//BD attribute
    private FirebaseUser firebaseUser;//user attribute
    private StorageReference storageReference;// storage reference for photo profile
    private Users mUsers;
    private static FragmentManager sFragmentManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile://view the profile user
                    sFragmentManager = getSupportFragmentManager();
                    if (findViewById(R.id.sw) != null){
                        FragmentTransaction fragmentTransaction =
                                sFragmentManager.beginTransaction();
                        Profile profile = new Profile();
                        fragmentTransaction.replace(R.id.sw, profile, null);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_dates://view all dates of the user
                    sFragmentManager = getSupportFragmentManager();
                    if (findViewById(R.id.sw) != null){
                        FragmentTransaction fragmentTransaction =
                                sFragmentManager.beginTransaction();
                        Citas citas = new Citas();
                        fragmentTransaction.replace(R.id.sw, citas, null);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_logout: //logout user
                    Logout lg = new Logout();
                    lg.show(getSupportFragmentManager(), "Cerrar sesión");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set firebase objects
        mUsers = new Users();
        database = mUsers.getDBR();
        firebaseUser = mUsers.getFU();
        storageReference = mUsers.getSR();
        setContentView(R.layout.activity_start);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override//register of the user
    public void applyLogin(String name, String id, String lasts, String born, String address, String auth, String type, String esp, Uri photo) {
        if (id == null){//if the user cancel the register
            AuthUI.getInstance()
                    .delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //return to login activity
                            startActivity(new Intent(Start.this, Login.class));
                            finish();
                        }
                    });
        }
        else {//if the user wants to registrate
            //profile data input
            mUsers.setData( "id", id, "Users");
            mUsers.setData( "nombre", name, "Users");
            mUsers.setData( "apellidos", lasts, "Users");
            mUsers.setData( "fecha nacimiento", born, "Users");
            mUsers.setData( "direccion", address, "Users");
            mUsers.setData( "auth", auth, "Users");
            mUsers.setData( "usuario", type, "Users");
            mUsers.setData( "especialidad", esp, "Users");
            mUsers.setPhoto(photo, "Users", "url");
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        //return super.onCreatePanelMenu(featureId, menu);
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//get the buttons event of menu
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logout(boolean lg) {
        if (lg) {//if the user wants to logout
            AuthUI.getInstance()
                    .signOut(Start.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Start.this, Login.class));
                        }
                    });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {// if the button back of the mobile is pressed
            Logout lg = new Logout();
            lg.show(getSupportFragmentManager(), "Cerrar sesión");
        }
        return super.onKeyDown(keyCode, event);
    }
}