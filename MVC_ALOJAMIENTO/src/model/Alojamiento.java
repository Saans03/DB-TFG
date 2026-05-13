package model;

public class Alojamiento {
    private int numaloj;
    private String alojamiento;
    private String nombreCompleto;
    private String responsable;
    private String direccion;
    private int distancia;

    public Alojamiento() {
        this.numaloj = 0;
        this.alojamiento = "";
        this.nombreCompleto = "";
        this.responsable = "";
        this.direccion = "";
        this.distancia = 0;
    }

    public Alojamiento(int numaloj, String alojamiento, String nombreCompleto, String responsable,
                       String direccion, int distancia) {
        this.numaloj = numaloj;
        this.alojamiento = alojamiento;
        this.nombreCompleto = nombreCompleto;
        this.responsable = responsable;
        this.direccion = direccion;
        this.distancia = distancia;
    }

    public Alojamiento(String alojamiento, String nombreCompleto, String responsable,
                       String direccion, int distancia) {
        this(0, alojamiento, nombreCompleto, responsable, direccion, distancia);
    }

    public int getNumaloj() { return numaloj; }
    public void setNumaloj(int numaloj) { this.numaloj = numaloj; }

    public String getAlojamiento() { return alojamiento; }
    public void setAlojamiento(String alojamiento) { this.alojamiento = alojamiento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getDistancia() { return distancia; }
    public void setDistancia(int distancia) { this.distancia = distancia; }

    @Override
    public String toString() {
        return "Alojamiento [numaloj=" + numaloj + ", alojamiento=" + alojamiento + 
               ", nombreCompleto=" + nombreCompleto + ", responsable=" + responsable + 
               ", direccion=" + direccion + ", distancia=" + distancia + "]";
    }
}

