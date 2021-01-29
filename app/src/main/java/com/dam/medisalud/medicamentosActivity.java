package com.dam.medisalud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link medicamentosActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class medicamentosActivity extends Fragment {
     private CalendarView calendar;
     private FirebaseAuth mAuth;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_medicamentos_activity,container,false);

        btnAgregarMedicamento = v.findViewById(R.id.btnAgregarMedicamento);
        btnAgregarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AgregarMedicamento = new Intent(getActivity(),AgregarMedicamento.class);
                AgregarMedicamento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(AgregarMedicamento);
            }
        });
        return v;
    }
}