package com.dam.medisalud;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link medicamentosActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class medicamentosActivity extends Fragment {

    private FirebaseAuth mAuth;
    private Button btnFecha;
    private Button btnHora;
    private Button btnRegistrar;
    private EditText textCantidad;
    private EditText textNombre;
    private TextView hora_n,fecha_n;


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
    public void validacion(){
        String nombre = textNombre.getText().toString();
        String cantidad = textCantidad.getText().toString();
        if(nombre.equals("")){
            textNombre.setError("Required");
        }else if(cantidad.equals("")){
            textCantidad.setError("Required");
        }
    }
    public void Limpiar(){
        fecha_n.setText("");
        hora_n.setText("");
        textNombre.setText("");
        textCantidad.setText("");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_medicamentos_activity,container,false);
        btnFecha = v.findViewById(R.id.btnFecha);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        textCantidad =v.findViewById(R.id.txtcantidadDosis);
        textNombre=v.findViewById(R.id.textnombrePastilla);
        fecha_n =v.findViewById(R.id.textViewFecha);
        hora_n=v.findViewById(R.id.textViewHora);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha =(dayOfMonth + "/" + "0"+(month+1) +"/" + year);
                        fecha_n.setText(fecha);
                    }
                },anio,mes,dia);
                dpd.show();
            }
        });
        System.out.println(textNombre.getText().toString());
        btnHora = v.findViewById(R.id.btnHora);
        btnHora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minuto = c.get(Calendar.MINUTE);
                TimePickerDialog tmd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hora= (hourOfDay + ":" + minute);
                        hora_n.setText(hora);
                    }
                },hora,minuto,true);
                tmd.show();
            }
        });

        btnRegistrar = v.findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = textNombre.getText().toString();
                String cantidad = textCantidad.getText().toString();
                String fecha = fecha_n.getText().toString();
                String hora = hora_n.getText().toString();
                String id = mAuth.getCurrentUser().getUid().toString();
                if(nombre.equals("") && cantidad.equals("")){
                    validacion();
                }else{
                    Medicamento m = new Medicamento();
                    m.setId(id);
                    m.setNombrePastilla(nombre);
                    m.setCantidadDosis(cantidad);
                    m.setHoraDosis(hora);
                    m.setFecha(fecha);
                    database.getReference("Medicamentos").child(UUID.randomUUID().toString()).setValue(m);
                    Toast.makeText(getActivity(),"Agregado", Toast.LENGTH_LONG).show();
                    Limpiar();
                }

            }
        });
        return v;
    }
}