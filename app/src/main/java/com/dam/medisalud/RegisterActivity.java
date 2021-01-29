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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG="LoginActivity";
    EditText edEmail, edPassword1, edPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edMail);
        edPassword1 = findViewById(R.id.edPassword1);
        edPassword2 = findViewById(R.id.edPassword2);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User: ",""+currentUser);
    }


    public void registrar(View view) {
        String email = edEmail.getText().toString();
        String password1 = edPassword1.getText().toString();
        String password2 = edPassword2.getText().toString();

        if(!email.isEmpty() && password1.length()>5) {

            if(password1.equals(password2)) {
                createAccount(email,password1);
                startActivity(new Intent(this,LoginActivity.class));
            } else {
                Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this,"La contraseña tiene que tener como minimo 5 digitos",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelar(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EXITO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });

    }
}