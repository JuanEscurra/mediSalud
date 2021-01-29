package com.dam.medisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AgregarMedicamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicamento);
    }

    public void cancelar(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

}