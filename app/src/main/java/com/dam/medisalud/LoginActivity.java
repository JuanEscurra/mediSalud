package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG="LoginActivity";
    EditText edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.fieldEmail);
        edPassword = findViewById(R.id.fieldPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void registrarte(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void ingresar(View view) {
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if(!email.isEmpty()&&!password.isEmpty()) {
            signIn(email,password);
            onStart();
        } else {
            Toast.makeText(LoginActivity.this, "Complete los campos",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(!user.isEmailVerified()) {
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this, "Verificar correo " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }
}