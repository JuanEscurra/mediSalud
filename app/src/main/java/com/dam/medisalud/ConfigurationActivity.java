package com.dam.medisalud;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigurationActivity#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConfigurationActivity extends Fragment {
    private FirebaseAuth mAuth;
    private TextView tvEmail;
    private Button btnCerrarSesion;
    private Button btnCambiarContrasenia;
    private Button btnPerfil;
    private Button btnInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigurationActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigurationActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigurationActivity newInstance(String param1, String param2) {
        ConfigurationActivity fragment = new ConfigurationActivity();
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

        /*
        tvEmail = findViewById(R.id.tvEmail);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent loginActivity = new Intent(MainActivity.this,LoginActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
                finish();
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            String email = currentUser.getEmail();
            tvEmail.setText(email);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_configuration_activity,container,false);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

        btnCerrarSesion = v.findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent loginActivity = new Intent(getActivity(),LoginActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginActivity);
            }
        });

        btnCambiarContrasenia = v.findViewById(R.id.btnCambiarContrasenia);
        btnPerfil= v.findViewById(R.id.btnPerfil);
        btnCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiarActivity = new Intent(getActivity(),CambiarContrasenia.class);
                cambiarActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(cambiarActivity);
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiarActivity = new Intent(getActivity(),DatosPersonales.class);
                cambiarActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(cambiarActivity);
            }
        });
        btnInfo = v.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"MEDISALUD\n"+"EMPRESA DEDICADA AL CONTROL DE MEDICAMENTOS DE PERSONAS MAYORES",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}