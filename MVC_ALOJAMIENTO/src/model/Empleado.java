package model;

public class Empleado {
    private int numemp;
    private String nombre;
    private int edad;
    private int alojamiento;
    private double sueldo;

    public Empleado(int numemp, String nombre, int edad, int alojamiento, double sueldo) {
        this.numemp = numemp;
        this.nombre = nombre;
        this.edad = edad;
        this.alojamiento = alojamiento;
        this.sueldo = sueldo;
    }
    public Empleado(String nombre, int edad, int alojamiento, double sueldo) {
        this.nombre = nombre;
        this.edad = edad;
        this.alojamiento = alojamiento;
        this.sueldo = sueldo;
    }

    public Empleado() {
        this.numemp = 0;
        this.nombre = "";
        this.edad = 0;
        this.alojamiento = 0;
        this.sueldo = 0.0;
    }

    
    public int getNumemp() { return numemp; }
    public void setNumemp(int numemp) { this.numemp = numemp; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getAlojamiento() { return alojamiento; }
    public void setAlojamiento(int alojamiento) { this.alojamiento = alojamiento; }

    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }
    @Override
    public String toString() {
        return "Empleado [numemp=" + numemp + ", nombre=" + nombre +
               ", edad=" + edad + ", alojamiento=" + alojamiento +
               ", sueldo=" + sueldo + "]";
    }
}

