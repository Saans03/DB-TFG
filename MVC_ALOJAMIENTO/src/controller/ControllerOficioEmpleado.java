package controller;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.OficioEmpleado;

public class ControllerOficioEmpleado {

    private Conexion conexion;

    public ControllerOficioEmpleado() {
        conexion = new Conexion();
    }
    public void insertar(OficioEmpleado oe) {
        String sql = "INSERT INTO oficioempleado (empleado, oficio, calificacion) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setInt(1, oe.getEmpleado());
            ps.setInt(2, oe.getOficio());
            ps.setString(3, oe.getCalificacion());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void editar(OficioEmpleado oe) {
        String sql = "UPDATE oficioempleado SET calificacion=? WHERE empleado=? AND oficio=?";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setString(1, oe.getCalificacion());
            ps.setInt(2, oe.getEmpleado());
            ps.setInt(3, oe.getOficio());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void borrar(int empleado, int oficio) {
        String sql = "DELETE FROM oficioempleado WHERE empleado=? AND oficio=?";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setInt(1, empleado);
            ps.setInt(2, oficio);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void cargarPorEmpleado(int numEmp, DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"ID Oficio", "Nombre Oficio", "Calificación"});

        String sql = "SELECT oe.oficio, o.oficio AS nombreOficio, oe.calificacion " +
                     "FROM oficioempleado oe " +
                     "JOIN oficio o ON oe.oficio = o.numoficio " +
                     "WHERE oe.empleado = ?";

        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setInt(1, numEmp);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("oficio");
                fila[1] = rs.getString("nombreOficio");
                fila[2] = rs.getString("calificacion");
                modelo.addRow(fila);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void cargarTodos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"Empleado", "Oficio", "Calificación"});

        String sql = "SELECT * FROM oficioempleado";
        try {
            Statement st = conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("empleado");
                fila[1] = rs.getInt("oficio");
                fila[2] = rs.getString("calificacion");
                modelo.addRow(fila);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
