package model;

public class Oficio {
    private int numoficio;
    private String oficio;
    private String descripcion;

    public Oficio() {
        this.numoficio = 0;
        this.oficio = "";
        this.descripcion = "";
    }

    public Oficio(int numoficio, String oficio, String descripcion) {
        this.numoficio = numoficio;
        this.oficio = oficio;
        this.descripcion = descripcion;
    }

    public Oficio(String oficio, String descripcion) {
        this(0, oficio, descripcion);
    }

    public int getNumoficio() { return numoficio; }
    public void setNumoficio(int numoficio) { this.numoficio = numoficio; }

    public String getOficio() { return oficio; }
    public void setOficio(String oficio) { this.oficio = oficio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Oficio [numoficio=" + numoficio + ", oficio=" + oficio + ", descripcion=" + descripcion + "]";
    }
}
