package com.example.menumaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMenu extends AppCompatActivity {
    Spinner spinner;
    Button buttonAñadirPlato;
    EditText nombrePlato;
    ImageView imagenPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);

        spinner = findViewById(R.id.SelectorCategoriaUserMenu);
        buttonAñadirPlato = findViewById(R.id.añadirPlatoButton);
        nombrePlato = findViewById(R.id.nombrePlatoUserMenu);
        imagenPerfil = findViewById(R.id.homeImagenCabezeraView);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);

        imagenPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(UserMenu.this, MainActivity.class);
            startActivity(intent);
        });

        buttonAñadirPlato.setOnClickListener(v -> {
            String idUsuario = checkCurrentUser();
            if (idUsuario != null) {
                String categoria = spinner.getSelectedItem().toString();
                String nombreDelPlato = nombrePlato.getText().toString();
                if (!nombreDelPlato.isEmpty()) {
                    insertarPlatos(idUsuario, categoria, nombreDelPlato);
                } else {
                    // Handle case where dish name is empty
                }
            } else {
                // Handle case where user is not authenticated
            }
        });
    }

    public String checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public void insertarPlatos(String idUsuario, String selectorCategoria, String nombrePlato) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://menumaker-2934c-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference(idUsuario);
        reference.child(selectorCategoria).push().setValue(nombrePlato);
    }
}