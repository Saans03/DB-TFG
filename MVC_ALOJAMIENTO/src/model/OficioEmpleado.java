package model;

public class OficioEmpleado {
    private int empleado;     
    private int oficio;        
    private String calificacion;
    public OficioEmpleado(int empleado, int oficio, String calificacion) {
        this.empleado = empleado;
        this.oficio = oficio;
        this.calificacion = calificacion;
    }
    public OficioEmpleado(int empleado, int oficio) {
        this.empleado = empleado;
        this.oficio = oficio;
        this.calificacion = "";
    }
    public OficioEmpleado() {
        this.empleado = 0;
        this.oficio = 0;
        this.calificacion = "";
    }

    public int getEmpleado() { return empleado; }
    public void setEmpleado(int empleado) { this.empleado = empleado; }

    public int getOficio() { return oficio; }
    public void setOficio(int oficio) { this.oficio = oficio; }

    public String getCalificacion() { return calificacion; }
    public void setCalificacion(String calificacion) { this.calificacion = calificacion; }
    @Override
    public String toString() {
        return "OficioEmpleado [empleado=" + empleado + ", oficio=" + oficio +
               ", calificacion=" + calificacion + "]";
    }
}
