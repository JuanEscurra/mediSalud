package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarContrasenia extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG="CambiarContrasenia";
    EditText tvContrasenaOne, tvContrasenaTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);

        mAuth = FirebaseAuth.getInstance();
        tvContrasenaOne = findViewById(R.id.tvContrasenaOne);
        tvContrasenaTwo = findViewById(R.id.tvContrasenaTwo);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void fnCambiarContrasenia(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if( tvContrasenaOne.getText().toString().equals( tvContrasenaTwo.getText().toString() )) {
            currentUser.updatePassword( tvContrasenaOne.getText().toString() )
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            }
                        }
                    });
        }
    }

}