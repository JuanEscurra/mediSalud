package com.dam.medisalud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class medicamentoRegistro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText txtFecha;
    private EditText txtHora;
    private Button btnFecha;
    private Button btnHora;
    private Button btnRegistrar;
    private Button btnVolver;
    private Button btnGuardar;
    private EditText textCantidad;
    private EditText textNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_registro);
        mAuth = FirebaseAuth.getInstance();
        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolver = findViewById(R.id.btnVolver);
        txtFecha = findViewById(R.id.editFecha);
        txtHora = findViewById(R.id.editHora);
        textNombre = findViewById(R.id.textnombrePastilla);
        textCantidad = findViewById(R.id.txtcantidadDosis);
        btnGuardar = findViewById(R.id.btnGuardar);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        Calendar calendar = Calendar.getInstance();
        btnFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(medicamentoRegistro.this, new DatePickerDialog.OnDateSetListener() {
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
        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minuto = c.get(Calendar.MINUTE);
                TimePickerDialog tmd = new TimePickerDialog(medicamentoRegistro.this, new TimePickerDialog.OnTimeSetListener() {
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


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = generateKey();
                long alertTime = calendar.getTimeInMillis()-System.currentTimeMillis();
                int random = (int) (Math.random()*50+1);
                Data data = guardarData("MEDISALUD : Recordatorio Tomar Pastilla",textNombre.getText().toString()+"Cantidad"+textCantidad.getText(),random);
                Workmanagernoti.guardarNoti(alertTime,data,tag);
                String nombre = textNombre.getText().toString();
                String cantidad = textCantidad.getText().toString();
                String fecha = txtFecha.getText().toString();
                String hora = txtHora.getText().toString();
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
                    Intent inte = new Intent(medicamentoRegistro.this,MainActivity.class);
                    inte.addFlags(inte.FLAG_ACTIVITY_NEW_TASK|inte.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inte);
                }

            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
        txtFecha.setText("");
        txtHora.setText("");
        textNombre.setText("");
        textCantidad.setText("");
    }
    private String generateKey(){
        return UUID.randomUUID().toString();
    }
    private Data guardarData(String titulo,String detalle,int id_noti){
        return new Data.Builder().putString("titulo",titulo).putString("detalle",detalle).putInt("id_noti",id_noti).build();
    }
    public void volver(View view){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
    }
}