package controller;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.Empleado;

public class ControllerEmpleado {

    private Conexion conexion;

    public ControllerEmpleado() {
        conexion = new Conexion();
    }

    public void insertar(Empleado e) {
        String sql = "INSERT INTO empleado (nombre, edad, alojamiento, sueldo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getNombre());

            if (e.getEdad() != 0) ps.setInt(2, e.getEdad());
            else ps.setNull(2, Types.INTEGER);

            if (e.getAlojamiento() != 0) ps.setInt(3, e.getAlojamiento());
            else ps.setNull(3, Types.INTEGER);

            ps.setDouble(4, e.getSueldo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setNumemp(rs.getInt(1));
            }

            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editar(Empleado e) {
        String sql = "UPDATE empleado SET nombre=?, edad=?, alojamiento=?, sueldo=? WHERE numemp=?";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setString(1, e.getNombre());

            if (e.getEdad() != 0) ps.setInt(2, e.getEdad());
            else ps.setNull(2, Types.INTEGER);

            if (e.getAlojamiento() != 0) ps.setInt(3, e.getAlojamiento());
            else ps.setNull(3, Types.INTEGER);

            ps.setDouble(4, e.getSueldo());
            ps.setInt(5, e.getNumemp());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void borrar(int numemp) {
        String sql = "DELETE FROM empleado WHERE numemp=?";
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(sql);
            ps.setInt(1, numemp);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void cargarTodos(DefaultTableModel modelo) {
        String sql = "SELECT numemp, nombre, edad, alojamiento, sueldo FROM empleado";
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Edad", "Alojamiento", "Sueldo"});

        try {
            Statement st = conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("numemp");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getInt("edad");
                fila[3] = rs.getInt("alojamiento");
                fila[4] = rs.getDouble("sueldo");
                modelo.addRow(fila);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


