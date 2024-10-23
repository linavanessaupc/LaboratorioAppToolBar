package co.edu.unipiloto.laboratorioapptoolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProyectAdapter.OnNoteLongClickListener {

    private List<Proyecto> proyectos;
    private ProyectAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupRecyclerView();

        proyectos = new ArrayList<>();
        noteAdapter = new ProyectAdapter(proyectos, this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onNoteLongClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar proyecto")
                .setMessage("¿Está seguro de que desea eliminar este proyecto?")
                .setPositiveButton("Eliminar", (dialog, which) -> deleteNoteAt(position))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            addNote();
            return true;
        } else if (id == R.id.action_share) {
            shareNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNote() {
        // EditText para el nombre del proyecto
        final EditText editTextNombre = new EditText(this);
        editTextNombre.setHint("Nombre del proyecto");

        final EditText editTextDescripcion = new EditText(this);
        editTextDescripcion.setHint("Descripción del proyecto");

        final Spinner categorySpinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editTextNombre);
        layout.addView(editTextDescripcion);
        layout.addView(categorySpinner);

        new AlertDialog.Builder(this)
                .setTitle("Nuevo proyecto")
                .setView(layout)
                .setPositiveButton("Agregar", (dialog, which) -> {
                    String nombre = editTextNombre.getText().toString();
                    String descripcion = editTextDescripcion.getText().toString();
                    String categoria = categorySpinner.getSelectedItem().toString();
                    if (!nombre.isEmpty() && !descripcion.isEmpty()) {
                        proyectos.add(new Proyecto(nombre, descripcion, categoria));
                        noteAdapter.notifyItemInserted(proyectos.size() - 1);
                    } else {
                        Toast.makeText(this, "El nombre y la descripción no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void deleteNoteAt(int position) {
        proyectos.remove(position);
        noteAdapter.notifyItemRemoved(position);
    }

    private void shareNotes() {
        StringBuilder notesText = new StringBuilder();
        for (Proyecto proyecto : proyectos) {
            notesText.append("Nombre: ").append(proyecto.getNombre())
                    .append("\nDescripción: ").append(proyecto.getDescripcion())
                    .append(" - Categoría: ").append(proyecto.getCategoria())
                    .append("\n\n");
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, notesText.toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Compartir proyectos"));
    }
}