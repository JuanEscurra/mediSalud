package com.dam.medisalud;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalActivity extends Fragment {
    private CalendarView calendar;
    private FirebaseAuth mAuth;
    private ArrayAdapter<Medicamento> adapter;
    private TextView fechaP;
    private Button agregar;
    private List<Medicamentox> listMedicamento = new ArrayList<Medicamentox>();
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
    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_principal_activity,container,false);
        calendar = v.findViewById(R.id.calendarV);
        String currentUser = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medisalud-68a8f-default-rtdb.firebaseio.com/");
        fechaP = v.findViewById(R.id.txtFecha);
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        int color = getActivity().getColor(R.color.black);
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarV, caldroidFragment);
        t.commit();
        database.getReference("Medicamentos").orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot x:snapshot.getChildren()) {
                    Medicamentox m =x.getValue(Medicamentox.class);
                    listMedicamento.add(m);
                }
                for(int i=0;i<listMedicamento.size();i++){
                    String fecha = String.valueOf(listMedicamento.get(i));
                    ColorDrawable negro = new ColorDrawable(color);
                    ColorDrawable green = new ColorDrawable(Color.GREEN);
                    ColorDrawable blue = new ColorDrawable(Color.BLUE);
                    Date date_fecha= ParseFecha(fecha);
                    if(i>=0 && i<=3){
                        caldroidFragment.setBackgroundDrawableForDate(green,date_fecha);
                        caldroidFragment.setTextColorForDate(R.color.white, date_fecha);
                        caldroidFragment.refreshView();
                    }else{
                        caldroidFragment.setBackgroundDrawableForDate(blue,date_fecha);
                        caldroidFragment.setTextColorForDate(R.color.white, date_fecha);
                        caldroidFragment.refreshView();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = format.format(date);
                Intent i = new Intent(getActivity(),listarFechas.class);
                i.putExtra("fechas",fecha);
                i.addFlags(i.FLAG_ACTIVITY_NEW_TASK|i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }


        };
        caldroidFragment.setCaldroidListener(listener);
        return v;
    }

}