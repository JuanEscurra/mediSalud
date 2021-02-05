package com.dam.medisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class MantenimientoMedicamento extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText txtNombre,txtFecha,txtHora,txtCantidad;
    private Button btnModificar,btnVolver,btnEliminar;
    private Button btnFecha,btnHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_medicamento);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        String nombrep = getIntent().getStringExtra("nombrep");
        String fechap = getIntent().getStringExtra("fechap");
        String cantidadp=getIntent().getStringExtra("cantidadp");
        String horap=getIntent().getStringExtra("horap");
        String uid = getIntent().getStringExtra("uid");
        System.out.println(uid);
        Calendar calendar = Calendar.getInstance();
        txtNombre = findViewById(R.id.txtNombrePastilla);
        txtFecha = findViewById(R.id.txtFecha);
        txtCantidad = findViewById(R.id.txtCantidadDosis);
        txtHora = findViewById(R.id.txtHora);
        txtNombre.setText(nombrep);
        txtFecha.setText(fechap);
        txtCantidad.setText(cantidadp);
        txtHora.setText(horap);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnVolver = findViewById(R.id.btnVolver);
        btnFecha = findViewById(R.id.btnFecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MantenimientoMedicamento.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(month<10 && dayOfMonth<10){
                            String fecha =("0"+dayOfMonth + "/" + "0"+(month+1) +"/" + year);
                            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            calendar.set(Calendar.MONTH,month);
                            calendar.set(Calendar.YEAR,year);
                            txtFecha.setText(fecha);
                        }else if(month<10){
                            String fecha =(dayOfMonth + "/" + "0"+(month+1) +"/" + year);
                            txtFecha.setText(fecha);
                        }else if(dayOfMonth<10){
                            String fecha =("0"+dayOfMonth + "/" + (month+1) +"/" + year);
                            txtFecha.setText(fecha);
                        }
                        else{
                            String fecha =(dayOfMonth + "/" + (month+1) +"/" + year);
                            txtFecha.setText(fecha);
                        }
                    }
                },anio,mes,dia);
                dpd.show();
            }
        });
        btnHora = findViewById(R.id.btnHora);
        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minuto = c.get(Calendar.MINUTE);
                TimePickerDialog tmd = new TimePickerDialog(MantenimientoMedicamento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        if(hourOfDay<10 && minute<10){
                            String hora = ("0"+hourOfDay + ":" + "0"+minute);
                            txtHora.setText(hora);
                        }else if(hourOfDay<10){
                            String hora = ("0"+hourOfDay + ":" + minute);
                            txtHora.setText(hora);
                        }else if(minute<10){
                            String hora = (hourOfDay + ":" +"0"+minute);
                            txtHora.setText(hora);
                        }
                        else{
                            String hora = (hourOfDay + ":" + minute);
                            txtHora.setText(hora);
                        }
                    }
                },hora,minuto,true);
                tmd.show();
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicamento m = new Medicamento();
                m.setNombrePastilla(txtNombre.getText().toString().trim());
                m.setCantidadDosis(txtCantidad.getText().toString().trim());
                m.setFecha(txtFecha.getText().toString());
                m.setHoraDosis(txtHora.getText().toString());
                m.setUid(uid);
                m.setId(mAuth.getCurrentUser().getUid().toString());
                database.getReference("Medicamentos").child(m.getUid()).setValue(m);
                Toast.makeText(MantenimientoMedicamento.this,"Registro modificado correctamente",Toast.LENGTH_LONG).show();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicamento m = new Medicamento();
                m.setUid(uid);
                database.getReference("Medicamentos").child(m.getUid()).removeValue();
                Limpiar();
                Toast.makeText(MantenimientoMedicamento.this,"Registro eliminado correctamente",Toast.LENGTH_LONG).show();
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MantenimientoMedicamento.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void Limpiar(){
        txtFecha.setText("");
        txtHora.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
    }
}