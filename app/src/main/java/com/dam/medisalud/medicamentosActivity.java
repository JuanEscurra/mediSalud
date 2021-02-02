package com.dam.medisalud;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link medicamentosActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class medicamentosActivity extends Fragment {

    private FirebaseAuth mAuth;
    private Button btnRegistrar;
    private ListView listaDatos;
    private EditText textCantidad;
    private EditText textNombre;
    private TextView hora_n,fecha_n;
    private List<Medicamento> listMedicamento = new ArrayList<Medicamento>();
    private ArrayAdapter<Medicamento> adapter;
    private Medicamento med;



    Button btnAgregarMedicamento;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public medicamentosActivity() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment medicamentosActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static medicamentosActivity newInstance(String param1, String param2) {
        medicamentosActivity fragment = new medicamentosActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.icon_add:
                Toast.makeText(getActivity(), "CTMR", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_medicamentos_activity,container,false);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        listaDatos = v.findViewById(R.id.ListView);
        database.getReference("Medicamentos").orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMedicamento.clear();
                for (DataSnapshot x:snapshot.getChildren()
                     ) {
                    Medicamento m = x.getValue(Medicamento.class);
                    if(m.getId().equals(mAuth.getCurrentUser().getUid().toString())){
                        listMedicamento.add(m);
                        if(getActivity()!=null){
                            adapter = new ArrayAdapter<Medicamento>(getActivity(), android.R.layout.simple_list_item_1,listMedicamento);
                            listaDatos.setAdapter(adapter);
                        }

                    }else{
                        System.out.println("ERROR XxXxXxX");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                med = (Medicamento) parent.getItemAtPosition(position);
                textNombre.setText(med.getNombrePastilla());
                textCantidad.setText(med.getCantidadDosis());
                fecha_n.setText(med.getFecha());
                hora_n.setText(med.getHoraDosis());
            }
        });
        btnRegistrar = v.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getActivity(),medicamentoRegistro.class);
                    i.addFlags(i.FLAG_ACTIVITY_NEW_TASK|i.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
            }
        });
        return v;
    }
}