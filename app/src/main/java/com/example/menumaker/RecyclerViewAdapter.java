package com.example.menumaker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlatoViewHolder> {
    private List<Plato> listaPlatos;
    private Context context;

    public RecyclerViewAdapter(List<Plato> platos, Context context) {
        this.listaPlatos = platos;
        this.context = context;
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

        Log.d("RecyclerViewAdapter", "Categoría: " + plato.getNombreCategoria());
        Log.d("RecyclerViewAdapter", "Plato: " + plato.getNombrePlato());
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    static class PlatoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategoria;
        private TextView textViewPlato;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
            textViewPlato = itemView.findViewById(R.id.textViewPlato);
        }
    }
}
