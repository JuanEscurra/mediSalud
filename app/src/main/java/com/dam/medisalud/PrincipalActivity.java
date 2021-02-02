package com.dam.medisalud;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalActivity extends Fragment {
    private CalendarView calendar;
    private FirebaseAuth mAuth;
    private ListView listViewMedicamento;
    private List<Medicamento> medicamentoList = new ArrayList<Medicamento>();
    private ArrayAdapter<Medicamento> adapter;
    private TextView fechaP;
    private Button agregar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrincipalActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalActivity newInstance(String param1, String param2) {
        PrincipalActivity fragment = new PrincipalActivity();
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
        View v = inflater.inflate(R.layout.fragment_principal_activity,container,false);
        calendar = v.findViewById(R.id.calendarV);
        String currentUser = mAuth.getCurrentUser().getUid();
        fechaP = v.findViewById(R.id.txtFecha);
        //listViewMedicamento = v.findViewById(R.id.ListViewMedicamento);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if(month<10 && dayOfMonth<10){
                    String fecha =("0"+dayOfMonth + "/" + "0"+(month+1) +"/" + year);
                    fechaP.setText(fecha);

                }else if(month<10){
                    String fecha =(dayOfMonth + "/" + "0"+(month+1) +"/" + year);
                    fechaP.setText(fecha);
                }else if(dayOfMonth<10){
                    String fecha =("0"+dayOfMonth + "/" + (month+1) +"/" + year);
                    fechaP.setText(fecha);

                }
                else {
                    String fecha = (dayOfMonth + "/" + (month + 1) + "/" + year);
                    fechaP.setText(fecha);
                }
                Intent i = new Intent(getActivity(),listarFechas.class);
                i.addFlags(i.FLAG_ACTIVITY_NEW_TASK|i.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("fechas",fechaP.getText().toString());
                startActivity(i);
            }


        });
        return v;
    }

}