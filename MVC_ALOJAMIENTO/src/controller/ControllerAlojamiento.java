package controller;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.Alojamiento;

public class ControllerAlojamiento {

    private Conexion conexion;

    public ControllerAlojamiento() {
        conexion = new Conexion();
    }

    public void insertar(Alojamiento a) {
        String sql = "INSERT INTO alojamiento (alojamiento, nombrecompleto, responsable, direccion, distancia) VALUES (?,?,?,?,?)";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getAlojamiento());
            ps.setString(2, a.getNombreCompleto());
            ps.setString(3, a.getResponsable());
            ps.setString(4, a.getDireccion());
            ps.setInt(5, a.getDistancia());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setNumaloj(rs.getInt(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void editar(Alojamiento a) {
        String sql = "UPDATE alojamiento SET alojamiento=?, nombrecompleto=?, responsable=?, direccion=?, distancia=? WHERE numaloj=?";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getAlojamiento());
            ps.setString(2, a.getNombreCompleto());
            ps.setString(3, a.getResponsable());
            ps.setString(4, a.getDireccion());
            ps.setInt(5, a.getDistancia());
            ps.setInt(6, a.getNumaloj());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM alojamiento WHERE numaloj=?";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void cargarTodos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new Object[]{"ID", "Alojamiento", "Nombre Completo", "Responsable", "Dirección", "Distancia"});
        String sql = "SELECT * FROM alojamiento";
        try (Connection con = conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("numaloj"),
                    rs.getString("alojamiento"),
                    rs.getString("nombrecompleto"),
                    rs.getString("responsable"),
                    rs.getString("direccion"),
                    rs.getInt("distancia")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
}


