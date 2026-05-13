package controller;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.Oficio;

public class ControllerOficio {

    private Conexion conexion;

    public ControllerOficio() {
        conexion = new Conexion();
    }

    public void insertar(Oficio o) {
        String sql = "INSERT INTO oficio (oficio, descripcion) VALUES (?,?)";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getOficio());
            ps.setString(2, o.getDescripcion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) o.setNumoficio(rs.getInt(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void editar(Oficio o) {
        String sql = "UPDATE oficio SET oficio=?, descripcion=? WHERE numoficio=?";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getOficio());
            ps.setString(2, o.getDescripcion());
            ps.setInt(3, o.getNumoficio());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM oficio WHERE numoficio=?";
        try (Connection con = conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void cargarTodos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new Object[]{"ID", "Oficio", "Descripción"});
        String sql = "SELECT * FROM oficio";
        try (Connection con = conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("numoficio"),
                    rs.getString("oficio"),
                    rs.getString("descripcion")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

