package co.edu.unipiloto.laboratorioapptoolbar;

public class Proyecto {

    private String nombre;
    private String descripcion;
    private String categoria;

    public Proyecto(String nombre, String descripcion, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }
}

