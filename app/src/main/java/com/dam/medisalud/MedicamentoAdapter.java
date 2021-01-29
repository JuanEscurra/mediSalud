package com.dam.medisalud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoviewHolder> {

    List<Medicamento> medicamento;

    public MedicamentoAdapter(List<Medicamento> medicamento) {
        this.medicamento = medicamento;
    }

    @NonNull
    @Override
    public MedicamentoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent,false);
        MedicamentoviewHolder holder = new MedicamentoviewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoviewHolder holder, int position) {
        Medicamento medicamentos = medicamento.get(position);
        holder.textViewPastilla.setText(medicamentos.getPastilla());
    }

    @Override
    public int getItemCount() {
        return medicamento.size();
    }

    public static class MedicamentoviewHolder extends RecyclerView.ViewHolder{

        TextView textViewPastilla;
        public MedicamentoviewHolder(View itemview){
            super(itemview);
            textViewPastilla = itemview.findViewById(R.id.textview_pastilla);


        }
    }
}
