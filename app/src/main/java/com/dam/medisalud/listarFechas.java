package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listarFechas extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private List<Medicamento> medicamentoList = new ArrayList<Medicamento>();
    private ArrayAdapter<Medicamento> adapter;
    private ListView listViewMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fechas);
        mAuth = FirebaseAuth.getInstance();
        String fecha = getIntent().getStringExtra("fechas");
        String currentUser = mAuth.getCurrentUser().getUid();
        listViewMedicamento = findViewById(R.id.ListViewMedicamento);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        database.getReference("Medicamentos").orderByChild("fecha").equalTo(fecha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                medicamentoList.clear();
                for (DataSnapshot x : snapshot.getChildren()
                ) {
                    Medicamento medicam = x.getValue(Medicamento.class);
                    if (medicam.getId().equals(currentUser)) {
                        medicamentoList.add(medicam);
                    } else {
                        System.out.println("ERROR");
                    }
                    adapter = new ArrayAdapter<Medicamento>(listarFechas.this, android.R.layout.simple_list_item_1, medicamentoList);
                    listViewMedicamento.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

        }
        });

    }

    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}