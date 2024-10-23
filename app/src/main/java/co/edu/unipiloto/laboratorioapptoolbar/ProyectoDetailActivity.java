package co.edu.unipiloto.laboratorioapptoolbar;

package co.edu.unipiloto.labtoolbar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProyectoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_detail);

        TextView descripcionTextView = findViewById(R.id.descripcionTextView);
        TextView categoriaTextView = findViewById(R.id.categoriaTextView);
        TextView nombreTextView = findViewById(R.id.nombreTextView); // Agregar el TextView para el nombre

        // Obtiene los datos del Intent
        String nombre = getIntent().getStringExtra("Nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
        String categoria = getIntent().getStringExtra("categoria");

        // Muestra la información en los TextViews
        nombreTextView.setText(nombre); // Mostrar nombre
        descripcionTextView.setText(descripcion);
        categoriaTextView.setText(categoria);
    }
}


