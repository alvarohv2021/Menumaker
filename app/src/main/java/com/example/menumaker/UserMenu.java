package com.example.menumaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMenu extends AppCompatActivity {
    Spinner spinner;
    Button buttonAñadirPlato;
    EditText nombrePlato;
    Spinner selectorCategoria;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        spinner = findViewById(R.id.SelectorCategoriaUserMenu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        buttonAñadirPlato = findViewById(R.id.añadirPlatoButton);
        nombrePlato = findViewById(R.id.nombrePlatoUserMenu);
        selectorCategoria = findViewById(R.id.SelectorCategoriaUserMenu);

        //Con esta parte de aquí recibimos le valor, en formato String, que tenia nuestro menu dropdown en el momento en el que pulsaron el boton
        selectorCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                categoria = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String idUsuario = checkCurrentUser();

        //Al pulsar el botón llama al metodo que inserta los datos desados, en este caso serian: idUsuario, categoria del plato y nombre del plato
        buttonAñadirPlato.setOnClickListener(v -> {
            insertarPlatos(idUsuario, categoria, nombrePlato.getText().toString());
        });
    }

    public String checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        // [END check_current_user]
        return null;
    }

    public void insertarPlatos(String idUsuario, String selectorCategoria, String nombrePlato) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://menumaker-2934c-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference(idUsuario);

        myRef.child(categoria).push().setValue(nombrePlato);
    }
}