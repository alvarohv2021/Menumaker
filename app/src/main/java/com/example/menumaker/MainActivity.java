package com.example.menumaker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DatabaseCallback {

    String idUsuario;
    Intent intent;
    ImageView imagenPerfil;
    TextView categoria1;
    TextView plato1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenPerfil = findViewById(R.id.homeImagenCabezeraView);
        categoria1 = findViewById(R.id.categoryName1);
        plato1 = findViewById(R.id.mealName1);

        //=============Recuperamos el id del usuario logeado, dado que es un nodo en la base de datos=============//
        idUsuario = checkCurrentUser();
        Log.d("FirebaseData", "ID Usuario: " + idUsuario);

        getData();

        //=============Hacer de la imagen un link al menu del usuario=============//
        imagenPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserMenu.class);
            startActivity(intent);
        });
    }

    public String checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("FirebaseData", "Typo de valor ID Usuario: " + user.getUid().getClass());
            return user.getUid();
        }
        return null;
    }

    public void getData() {
        if (idUsuario != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://menumaker-2934c-default-rtdb.europe-west1.firebasedatabase.app/").getReference(idUsuario).child("ARROZ");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Llama al método onCallback de la interfaz con los datos.
                    onCallback(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Llama al método onError de la interfaz en caso de error.
                    onError(databaseError);
                }
            });
        } else {
            Log.e("FirebaseError", "ID de usuario nulo");
        }
    }

    @Override
    public void onCallback(DataSnapshot dataSnapshot) {
        // Maneja los datos de forma síncrona aquí.
        // Por ejemplo:
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            String key = childSnapshot.getKey();
            String value = childSnapshot.getValue(String.class);
            categoria1.setText(key);
            plato1.setText(value);
            Log.d("FirebaseData", "Key: " + key + ", Valor: " + value);
        }
    }

    @Override
    public void onError(DatabaseError databaseError) {
        // Maneja los errores de forma síncrona aquí.
        Log.e("FirebaseError", "Error al obtener datos de ARROZ: " + databaseError.getMessage());
    }
}
