package com.example.menumaker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlatoViewHolder> {
    private List<Plato> listaPlatos;
    private Context context;
    private DatabaseReference dbReference;

    public RecyclerViewAdapter(List<Plato> platos, Context context, DatabaseReference dbReference) {
        this.listaPlatos = platos;
        this.context = context;
        this.dbReference = dbReference;

    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_recycler_view_element, parent, false);
        return new RecyclerViewAdapter.PlatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {

        Plato plato = listaPlatos.get(position);

        // Establecer los datos en las vistas del ViewHolder
        holder.textViewCategoria.setText(plato.getNombreCategoria());
        holder.textViewPlato.setText(plato.getNombrePlato());
        holder.idPlato.setText(plato.getIdPlato());
        holder.botonEliminarPlato.setOnClickListener(v -> {
            listaPlatos.remove(position);
            dbReference.child(plato.getNombreCategoria()).child(plato.getIdPlato()).removeValue();

            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    static class PlatoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategoria;
        private TextView textViewPlato;
        private Button botonEliminarPlato;
        private TextView idPlato;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
            textViewPlato = itemView.findViewById(R.id.textViewPlato);
            botonEliminarPlato = itemView.findViewById(R.id.removElementButton);
            idPlato = itemView.findViewById(R.id.id);
        }
    }
}
