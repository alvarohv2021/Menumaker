package com.example.menumaker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String idUsuario;
    DatabaseReference reference;
    ImageView imagenPerfil;
    List<Plato> platos;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    Plato plato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        imagenPerfil = findViewById(R.id.homeImagenCabezeraView);

        //==========Recuperamos el id del usuario logeado==========//
        idUsuario = checkCurrentUser();

        //==========Recuperamos la referencia de nuestra base de datos==========//
        reference = getDBReference();

        //==========Recibimos los datos y trabajamos con ellos==========//
        platos = new ArrayList<>();
        adapter = new RecyclerViewAdapter(platos, MainActivity.this, reference); // Inicializar el adaptador
        recyclerView.setAdapter(adapter); // Configurar el adaptador en el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getData(reference); // Obtener los datos de Firebase

        //==========Hacemos de la imágen de perfil, un link para ir al menu del usuario==========//
        imagenPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserMenu.class);
            startActivity(intent);
        });
    }


    public String checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public DatabaseReference getDBReference() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://menumaker-2934c-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(idUsuario);
        return reference;
    }

    ;

    public void getData(DatabaseReference reference) {
        if (idUsuario != null) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren()) {
                        String categoria = categoriaSnapshot.getKey(); // Obtener la categoría del plato

                        for (DataSnapshot platoSnapshot : categoriaSnapshot.getChildren()) {
                            String idPlato = platoSnapshot.getKey();
                            String platoValue = platoSnapshot.getValue(String.class); // Obtener el nombre del plato
                            plato = new Plato(categoria, platoValue, idPlato);//Objeto "plato", los parametros son la informacion recibida
                            platos.add(plato);//Añadimos cada plato a la lista de platos

                        }
                    }
                    Collections.shuffle(platos);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseError", "Error al obtener datos de ARROZ: " + databaseError.getMessage());
                }
            });
        } else {
            Log.e("FirebaseError", "ID de usuario nulo");
        }
    }
}

