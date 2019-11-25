package com.trixx.p9runtimefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager sFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sFragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null){
            // Condicion para no empalmar fragmentos
            if (savedInstanceState != null){
                return;
            }
            FragmentTransaction fragmentTransaction =
                    sFragmentManager.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.fragment_container, homeFragment, null);
            fragmentTransaction.commit();
        }
    }
}
