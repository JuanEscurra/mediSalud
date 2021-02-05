package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private Medicamento med;

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
        listViewMedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                med = (Medicamento) parent.getItemAtPosition(position);
                String nombrePastilla = med.getNombrePastilla();
                String cantidadDosis = med.getCantidadDosis();
                String fechaPastilla = med.getFecha();
                String horaDosis = med.getHoraDosis();
                String id_per = med.getId();
                String id_med = med.getUid();
                Intent i = new Intent(listarFechas.this,MantenimientoMedicamento.class);
                i.putExtra("nombrep",nombrePastilla);
                i.putExtra("cantidadp",cantidadDosis);
                i.putExtra("fechap",fechaPastilla);
                i.putExtra("horap",horaDosis);
                i.putExtra("uid",id_med);
                i.addFlags(i.FLAG_ACTIVITY_NEW_TASK|i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}