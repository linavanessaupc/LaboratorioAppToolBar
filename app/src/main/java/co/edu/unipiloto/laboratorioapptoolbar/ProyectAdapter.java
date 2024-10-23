package co.edu.unipiloto.laboratorioapptoolbar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProyectAdapter extends RecyclerView.Adapter<ProyectAdapter.ViewHolder> {

    private List<Proyecto> proyectos;
    private OnNoteLongClickListener longClickListener;
    private Context context;

    public interface OnNoteLongClickListener {
        void onNoteLongClick(int position);
    }

    public ProyectAdapter(List<Proyecto> proyectos, Context context, OnNoteLongClickListener longClickListener) {
        this.proyectos = proyectos;
        this.context = context;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Proyecto proyecto = proyectos.get(position);
        holder.bind(proyecto);

        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onNoteLongClick(position);
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProyectoDetailActivity.class);
            intent.putExtra("Nombre", proyecto.getNombre());
            intent.putExtra("descripcion", proyecto.getDescripcion());
            intent.putExtra("categoria", proyecto.getCategoria());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return proyectos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView descripcionTextView;
        public TextView categoriaTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            categoriaTextView = itemView.findViewById(R.id.categoriaTextView);
        }

        public void bind(Proyecto proyecto) {
            descripcionTextView.setText(proyecto.getDescripcion());
            categoriaTextView.setText(proyecto.getCategoria());
        }
    }
}

