package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView tvEmail;
    private Button btnCerrarSesion;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.principalActivity:
                            selectedFragment = new PrincipalActivity();
                            break;
                        case R.id.medicamentosActivity:
                            selectedFragment = new medicamentosActivity();
                            break;
                        case R.id.configurationActivity:
                            selectedFragment = new ConfigurationActivity();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            selectedFragment).commit();
                    return true;
                }
            };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);


        mAuth = FirebaseAuth.getInstance();

        /*
        tvEmail = findViewById(R.id.tvEmail);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent loginActivity = new Intent(MainActivity.this,LoginActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
                finish();
            }
        });*/
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            startActivity(new Intent(this,LoginActivity.class));
        } /*else {
            String email = currentUser.getEmail();
            tvEmail.setText(email);
        }*/
    }


}