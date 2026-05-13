package view;

import javax.swing.*;
import controller.ControllerOficio;
import model.Oficio;

public class jfOficio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfOficio, tfDescripcion;
    private JButton btnGuardar;
    private Oficio oficio;

    public jfOficio() { this(null); }

    public jfOficio(Oficio oficio) {
        this.oficio = oficio;

        setTitle("Oficio");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbOficio = new JLabel("Oficio:");
        lbOficio.setBounds(20, 20, 100, 25);
        add(lbOficio);

        tfOficio = new JTextField();
        tfOficio.setBounds(130, 20, 180, 25);
        add(tfOficio);

        JLabel lbDescripcion = new JLabel("Descripción:");
        lbDescripcion.setBounds(20, 60, 100, 25);
        add(lbDescripcion);

        tfDescripcion = new JTextField();
        tfDescripcion.setBounds(130, 60, 180, 25);
        add(tfDescripcion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(130, 120, 100, 30);
        add(btnGuardar);

        if (oficio != null) {
            tfOficio.setText(oficio.getOficio());
            tfDescripcion.setText(oficio.getDescripcion());
        }

        btnGuardar.addActionListener(e -> {
            ControllerOficio co = new ControllerOficio();
            if (oficio == null) {
                Oficio nuevo = new Oficio(tfOficio.getText(), tfDescripcion.getText());
                co.insertar(nuevo);
            } else {
                oficio.setOficio(tfOficio.getText());
                oficio.setDescripcion(tfDescripcion.getText());
                co.editar(oficio);
            }
            dispose();
        });
    }
}



