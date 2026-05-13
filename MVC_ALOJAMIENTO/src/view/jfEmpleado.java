package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.ControllerEmpleado;
import controller.ControllerOficio;
import controller.ControllerOficioEmpleado;
import model.Empleado;
import model.Oficio;
import model.OficioEmpleado;
import java.awt.Font;
import java.util.ArrayList;

public class jfEmpleado extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField tfNombre, tfEdad, tfSueldo;
    private JComboBox<Oficio> cbOficios;
    private JTable tablaOficioEmpleado;
    private DefaultTableModel modeloOficioEmpleado;
    private JComboBox<String> cbAlojamiento;

    private JButton btnGuardar;

    private Empleado emp;
    private ArrayList<Oficio> listaOficios;

    public jfEmpleado() {
        this(null);
    }

    public jfEmpleado(Empleado emp) {
        this.emp = emp;

        setTitle("Empleado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        JLabel lbNombre = new JLabel("Nombre:");
        lbNombre.setBounds(20, 20, 100, 25);
        add(lbNombre);

        tfNombre = new JTextField();
        tfNombre.setBounds(130, 20, 200, 25);
        add(tfNombre);

        JLabel lbEdad = new JLabel("Edad:");
        lbEdad.setBounds(20, 60, 100, 25);
        add(lbEdad);

        tfEdad = new JTextField();
        tfEdad.setBounds(130, 60, 200, 25);
        add(tfEdad);

        JLabel lbAloj = new JLabel("Alojamiento:");
        lbAloj.setBounds(20, 100, 100, 25);
        add(lbAloj);

        cbAlojamiento = new JComboBox<>();
        cbAlojamiento.setBounds(130, 100, 200, 25);
        add(cbAlojamiento);

        JLabel lbSueldo = new JLabel("Sueldo:");
        lbSueldo.setBounds(20, 140, 100, 25);
        add(lbSueldo);

        tfSueldo = new JTextField();
        tfSueldo.setBounds(130, 140, 200, 25);
        add(tfSueldo);


        JLabel lbOficio = new JLabel("Oficios:");
        lbOficio.setBounds(20, 180, 100, 25);
        add(lbOficio);

        modeloOficioEmpleado = new DefaultTableModel(new Object[]{"ID", "Oficio", "Calificación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; 
            }
        };

        tablaOficioEmpleado = new JTable(modeloOficioEmpleado);
        JScrollPane scrollOficios = new JScrollPane(tablaOficioEmpleado);
        scrollOficios.setBounds(130, 180, 400, 200);
        add(scrollOficios);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(130, 400, 120, 30);
        add(btnGuardar);

        cargarAlojamientos();
        cargarOficios();

        if (emp != null) {
            tfNombre.setText(emp.getNombre());
            tfEdad.setText(emp.getEdad() != 0 ? String.valueOf(emp.getEdad()) : "");
            tfSueldo.setText(emp.getSueldo() != 0 ? String.valueOf(emp.getSueldo()) : "");

            if (emp.getAlojamiento() != 0) {
                for (int i = 0; i < cbAlojamiento.getItemCount(); i++) {
                    if (cbAlojamiento.getItemAt(i).split(" - ")[0].equals(String.valueOf(emp.getAlojamiento()))) {
                        cbAlojamiento.setSelectedIndex(i);
                        break;
                    }
                }
            }

            new ControllerOficioEmpleado().cargarPorEmpleado(emp.getNumemp(), modeloOficioEmpleado);
        }

        btnGuardar.addActionListener(e -> {
            ControllerEmpleado ce = new ControllerEmpleado();
            ControllerOficioEmpleado coe = new ControllerOficioEmpleado();

            try {
                String nombre = tfNombre.getText();
                Integer edad = tfEdad.getText().isEmpty() ? 0 : Integer.parseInt(tfEdad.getText());
                Integer alojamiento = cbAlojamiento.getSelectedIndex() <= 0 ? 0 :
                        Integer.parseInt(cbAlojamiento.getSelectedItem().toString().split(" - ")[0]);
                Double sueldo = tfSueldo.getText().isEmpty() ? 0.0 : Double.parseDouble(tfSueldo.getText());

                if (emp == null) {
                    Empleado nuevo = new Empleado(nombre, edad, alojamiento, sueldo);
                    ce.insertar(nuevo);
                    for (int i = 0; i < modeloOficioEmpleado.getRowCount(); i++) {
                        int idOficio = (int) modeloOficioEmpleado.getValueAt(i, 0);
                        String cal = modeloOficioEmpleado.getValueAt(i, 2) != null ?
                                modeloOficioEmpleado.getValueAt(i, 2).toString() : "";
                        coe.insertar(new OficioEmpleado(nuevo.getNumemp(), idOficio, cal));
                    }
                } else {
                    emp.setNombre(nombre);
                    emp.setEdad(edad);
                    emp.setAlojamiento(alojamiento);
                    emp.setSueldo(sueldo);
                    ce.editar(emp);
                    for (int i = 0; i < modeloOficioEmpleado.getRowCount(); i++) {
                        int idOficio = (int) modeloOficioEmpleado.getValueAt(i, 0);
                        String cal = modeloOficioEmpleado.getValueAt(i, 2) != null ?
                                modeloOficioEmpleado.getValueAt(i, 2).toString() : "";

                        coe.editar(new OficioEmpleado(emp.getNumemp(), idOficio, cal));
                    }
                }
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Escriba valores correctos al formato",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void cargarAlojamientos() {
        cbAlojamiento.addItem("0 - Sin alojamiento"); 
        try {
            DefaultTableModel modelTemp = new DefaultTableModel();
            new controller.ControllerAlojamiento().cargarTodos(modelTemp);
            for (int i = 0; i < modelTemp.getRowCount(); i++) {
                int id = (int) modelTemp.getValueAt(i, 0);
                String nombre = (String) modelTemp.getValueAt(i, 1);
                cbAlojamiento.addItem(id + " - " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarOficios() {
        listaOficios = new ArrayList<>();
        try {
            DefaultTableModel modelTemp = new DefaultTableModel();
            new ControllerOficio().cargarTodos(modelTemp);

            for (int i = 0; i < modelTemp.getRowCount(); i++) {
                int id = (int) modelTemp.getValueAt(i, 0);
                String nombre = (String) modelTemp.getValueAt(i, 1);
                listaOficios.add(new Oficio(id, nombre, ""));
                modeloOficioEmpleado.addRow(new Object[]{id, nombre, ""});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

