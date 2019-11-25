package com.trixx.medcenter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity{
private FirebaseUser user;
private List<AuthUI.IdpConfig> providers;
private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start authentication user
        user =  FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){// if it is no logged
            //select providers to login
            providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.drawable.app)
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }
        else {
            startActivity(new Intent(LoginActivity.this, Inicio.class));//start activity
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {//if it is a sig in code
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {//if it is successful
                // Successfully signed in
                startActivity(new Intent(LoginActivity.this, Inicio.class));
            }

            else {//if it is not successful
                Toast.makeText(this, "Error: " + response.getError().getMessage(), Toast.LENGTH_LONG).show();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }
}