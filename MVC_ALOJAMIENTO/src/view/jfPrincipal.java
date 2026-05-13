package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.ControllerAlojamiento;
import controller.ControllerEmpleado;
import controller.ControllerOficio;
import controller.ControllerOficioEmpleado;
import model.Alojamiento;
import model.Empleado;
import model.Oficio;
import java.awt.Font;

public class jfPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaAlojamientos, tablaEmpleados, tablaOficios, tablaOficioEmpleado;
    private DefaultTableModel modeloAlojamientos, modeloEmpleados, modeloOficios, modeloOficioEmpleado;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            jfPrincipal frame = new jfPrincipal();
            frame.setVisible(true);
        });
    }

    public jfPrincipal() {
        setTitle("Sistema de Gestión");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 684, 300);
        add(tabbedPane);

        JPanel panelA = new JPanel(null);
        modeloAlojamientos = new DefaultTableModel(
            new Object[]{"ID", "Alojamiento", "Nombre Completo", "Responsable", "Dirección", "Distancia"}, 0);
        tablaAlojamientos = new JTable(modeloAlojamientos);
        JScrollPane scrollA = new JScrollPane(tablaAlojamientos);
        scrollA.setBounds(0, 0, 679, 270);
        panelA.add(scrollA);
        tabbedPane.addTab("Alojamientos", panelA);

        
        JPanel panelE = new JPanel(null);
        modeloEmpleados = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Edad", "Alojamiento", "Sueldo"}, 0);
        tablaEmpleados = new JTable(modeloEmpleados);
        JScrollPane scrollEmpleados = new JScrollPane(tablaEmpleados);
        modeloOficioEmpleado = new DefaultTableModel();
        tablaOficioEmpleado = new JTable(modeloOficioEmpleado);
        JScrollPane scrollOficioEmpleado = new JScrollPane(tablaOficioEmpleado);
        JSplitPane splitEmpleados = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollEmpleados, scrollOficioEmpleado);
        splitEmpleados.setBounds(0, 0, 679, 270);
        splitEmpleados.setDividerLocation(340);
        panelE.add(splitEmpleados);
        tabbedPane.addTab("Empleados", panelE);

        
        JPanel panelO = new JPanel(null);
        modeloOficios = new DefaultTableModel(
            new Object[]{"ID", "Oficio", "Descripción"}, 0);
        tablaOficios = new JTable(modeloOficios);
        JScrollPane scrollO = new JScrollPane(tablaOficios);
        scrollO.setBounds(0, 0, 679, 270);
        panelO.add(scrollO);
        tabbedPane.addTab("Oficios", panelO);

        
        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setFont(new Font("Papyrus", Font.BOLD, 15));
        btnNuevo.setBounds(20, 330, 120, 40);
        add(btnNuevo);
        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Papyrus", Font.BOLD, 15));
        btnEditar.setBounds(150, 330, 120, 40);
        add(btnEditar);
        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.setFont(new Font("Papyrus", Font.BOLD, 15));
        btnBorrar.setBounds(280, 330, 120, 40);
        add(btnBorrar);
        JButton btnRefrescar = new JButton("Refrescar"); 
        btnRefrescar.setFont(new Font("Papyrus", Font.BOLD, 15));
        btnRefrescar.setBounds(410, 330, 120, 40);
        add(btnRefrescar);
        btnRefrescar.addActionListener(e -> refrescarTablas());
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Papyrus", Font.BOLD, 15));
        btnSalir.setBounds(540, 330, 120, 40);
        add(btnSalir);
        btnSalir.addActionListener(e -> System.exit(0));
        btnNuevo.addActionListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            switch (index) {
                case 0 -> new jfAlojamiento().setVisible(true);
                case 1 -> new jfEmpleado().setVisible(true);
                case 2 -> new jfOficio().setVisible(true);}});
        btnEditar.addActionListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            switch (index) {
                case 0 -> editarAlojamiento();
                case 1 -> editarEmpleado();
                case 2 -> editarOficio();}});
        btnBorrar.addActionListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            switch (index) {
                case 0 -> borrarAlojamiento();
                case 1 -> borrarEmpleado();
                case 2 -> borrarOficio();
            }
            refrescarTablas();
        });

        tablaEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = tablaEmpleados.getSelectedRow();
                if (fila >= 0) {
                    int numEmp = (int) modeloEmpleados.getValueAt(fila, 0);
                    new ControllerOficioEmpleado().cargarPorEmpleado(numEmp, modeloOficioEmpleado);
                } else {
                    modeloOficioEmpleado.setRowCount(0);
                }
            }
        });

        refrescarTablas();
    }

    private void refrescarTablas() {
        modeloAlojamientos.setRowCount(0);
        modeloEmpleados.setRowCount(0);
        modeloOficios.setRowCount(0);
        modeloOficioEmpleado.setRowCount(0);

        new ControllerAlojamiento().cargarTodos(modeloAlojamientos);
        new ControllerEmpleado().cargarTodos(modeloEmpleados);
        new ControllerOficio().cargarTodos(modeloOficios);
    }

    private void editarAlojamiento() {
        int fila = tablaAlojamientos.getSelectedRow();
        if (fila >= 0) {
            Alojamiento a = new Alojamiento(
                    (String) modeloAlojamientos.getValueAt(fila, 1),
                    (String) modeloAlojamientos.getValueAt(fila, 2),
                    (String) modeloAlojamientos.getValueAt(fila, 3),
                    (String) modeloAlojamientos.getValueAt(fila, 4),
                    modeloAlojamientos.getValueAt(fila, 5) != null ? (int) modeloAlojamientos.getValueAt(fila, 5) : 0
            );
            a.setNumaloj((int) modeloAlojamientos.getValueAt(fila, 0));
            new jfAlojamiento(a).setVisible(true);
        }
    }

    private void editarEmpleado() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila >= 0) {
            Integer edad = modeloEmpleados.getValueAt(fila, 2) != null ? (Integer) modeloEmpleados.getValueAt(fila, 2) : null;
            Integer aloj = modeloEmpleados.getValueAt(fila, 3) != null ? (Integer) modeloEmpleados.getValueAt(fila, 3) : null;
            Double sueldo = modeloEmpleados.getValueAt(fila, 4) != null ? (Double) modeloEmpleados.getValueAt(fila, 4) : null;

            Empleado e = new Empleado(
                    (String) modeloEmpleados.getValueAt(fila, 1),
                    edad,
                    aloj,
                    sueldo
            );
            e.setNumemp((int) modeloEmpleados.getValueAt(fila, 0));
            new jfEmpleado(e).setVisible(true);
        }
    }

    private void editarOficio() {
        int fila = tablaOficios.getSelectedRow();
        if (fila >= 0) {
            Oficio o = new Oficio(
                    (String) modeloOficios.getValueAt(fila, 1),
                    (String) modeloOficios.getValueAt(fila, 2)
            );
            o.setNumoficio((int) modeloOficios.getValueAt(fila, 0));
            new jfOficio(o).setVisible(true);
        }
    }

    private void borrarAlojamiento() {
        int fila = tablaAlojamientos.getSelectedRow();
        if (fila >= 0) {
            new ControllerAlojamiento().borrar((int) modeloAlojamientos.getValueAt(fila, 0));
        }
    }

    private void borrarEmpleado() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila >= 0) {
            new ControllerEmpleado().borrar((int) modeloEmpleados.getValueAt(fila, 0));
        }
    }

    private void borrarOficio() {
        int fila = tablaOficios.getSelectedRow();
        if (fila >= 0) {
            new ControllerOficio().borrar((int) modeloOficios.getValueAt(fila, 0));
        }
    }
}
