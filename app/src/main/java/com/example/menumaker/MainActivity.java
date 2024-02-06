package com.example.menumaker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Intent intent;
    String idUsuario;
    Button botonEspera;
    ImageView imagenPerfil;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenPerfil = findViewById(R.id.homeImagenCabezeraView);
        botonEspera = findViewById(R.id.botonEspera);

        //-------------Section------------//
        idUsuario = checkCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        // Leer datos de manera síncrona
        readData();

        //Hazemos, de la imagen de perfil en la cabezera, un link a la página de perfil del usuario
        imagenPerfil.setOnClickListener(v -> {
            intent = new Intent(this, UserMenu.class);
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

    private void readData() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Aquí puedes manejar los datos de la base de datos
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getKey();
                    Log.d("MainActivity", "Category: " + category);
                    for (DataSnapshot itemSnapshot : categorySnapshot.getChildren()) {
                        String itemId = itemSnapshot.getKey();
                        String itemName = (String) itemSnapshot.getValue();
                        Log.d("MainActivity", "Item ID: " + itemId + ", Item Name: " + itemName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de datos aquí
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }
}
