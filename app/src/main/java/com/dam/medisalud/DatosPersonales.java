package com.dam.medisalud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatosPersonales extends AppCompatActivity {
        private EditText txtNombre,txtApellido,txtCorreo;
        private TextView txtValor;
        private List<Usuario> listUsuarios = new ArrayList<Usuario>();
        private Button btnModificar;
        private Button btnVolver;
        private FirebaseAuth mAuth;
        private Usuario us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        String idUser = mAuth.getCurrentUser().getUid();
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtValor = findViewById(R.id.txtUID);
        database.getReference("Usuarios").orderByChild("id").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listUsuarios.clear();
                for (DataSnapshot x:snapshot.getChildren()
                     ) {
                    Usuario user = x.getValue(Usuario.class);
                    String nombre = user.getNombres();
                    String apellido = user.getApellidos();
                    String correo = user.getCorreo();
                    String uid = user.getUid();
                    txtNombre.setText(nombre);
                    txtApellido.setText(apellido);
                    txtCorreo.setText(correo);
                    txtValor.setText(uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnModificar = findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                u.setId(mAuth.getCurrentUser().getUid().toString());
                u.setNombres(txtNombre.getText().toString().trim());
                u.setApellidos(txtApellido.getText().toString().trim());
                u.setCorreo(txtCorreo.getText().toString().trim());
                u.setUid(txtValor.getText().toString().trim());
                database.getReference("Usuarios").child(u.getUid()).setValue(u);
                Toast.makeText(DatosPersonales.this,"MEDISALUD\n"+"Registro modificado correctamente",Toast.LENGTH_LONG).show();
            }
        });
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosPersonales.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}