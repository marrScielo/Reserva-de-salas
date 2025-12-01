package ui;

import domain.Reserva;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NuevaReservaPanel extends JPanel {
    private final JComboBox<Integer> cbInicio = new JComboBox<Integer>();
    private final JComboBox<Integer> cbFin = new JComboBox<Integer>();

    public NuevaReservaPanel(MainFrame frame){
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx=0; c.gridy=0;

        JPanel card = UIUtils.padded(20, 20, 20, 20, new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                "Nueva Reserva",
                TitledBorder.LEFT, TitledBorder.TOP,
                getFont().deriveFont(Font.BOLD)));

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(8,8,8,8);
        f.gridx=0; f.gridy=0; f.anchor=GridBagConstraints.WEST;

        JLabel salaLbl = new JLabel("Sala:");
        salaLbl.setFont(Theme.body(salaLbl));
        card.add(salaLbl, f);

        f.gridx=1;
        JTextField salaValue = new JTextField(frame.ctx.sala.getNombre());
        salaValue.setEditable(false);
        salaValue.setColumns(16);
        card.add(salaValue, f);

        f.gridx=0; f.gridy++;
        JLabel fechaLbl = new JLabel("Fecha:");
        fechaLbl.setFont(Theme.body(fechaLbl));
        card.add(fechaLbl, f);

        f.gridx=1;
        JTextField fechaValue = new JTextField(LocalDate.now().toString());
        fechaValue.setEditable(false);
        card.add(fechaValue, f);

        f.gridx=0; f.gridy++;
        JLabel iniLbl = new JLabel("Hora inicio (08–18):");
        iniLbl.setFont(Theme.body(iniLbl));
        card.add(iniLbl, f);

        f.gridx=1;
        for(int h=8; h<=18; h++) cbInicio.addItem(h);
        card.add(cbInicio, f);

        f.gridx=0; f.gridy++;
        JLabel finLbl = new JLabel("Hora fin (09–19):");
        finLbl.setFont(Theme.body(finLbl));
        card.add(finLbl, f);

        f.gridx=1;
        for(int h=9; h<=19; h++) cbFin.addItem(h);
        card.add(cbFin, f);

        f.gridx=0; f.gridy++;
        JButton back = UIUtils.secondaryButton("← Volver");
        card.add(back, f);

        f.gridx=1;
        JButton reservar = UIUtils.primaryButton("Reservar");
        card.add(reservar, f);

        add(card, c);

        back.addActionListener(e -> frame.showScreen(MainFrame.HOME));

        reservar.addActionListener(e -> {
            int h1 = (Integer) cbInicio.getSelectedItem();
            int h2 = (Integer) cbFin.getSelectedItem();
            if (h2 <= h1) {
                JOptionPane.showMessageDialog(this,
                        "La hora fin debe ser mayor a la hora inicio.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Reserva r = new Reserva(
                    "R" + System.currentTimeMillis(),
                    frame.ctx.sala.getId(),
                    frame.ctx.usuario.getId(),
                    LocalDateTime.of(LocalDate.now().getYear(),
                                     LocalDate.now().getMonthValue(),
                                     LocalDate.now().getDayOfMonth(),
                                     h1, 0),
                    LocalDateTime.of(LocalDate.now().getYear(),
                                     LocalDate.now().getMonthValue(),
                                     LocalDate.now().getDayOfMonth(),
                                     h2, 0)
            );
            try {
                frame.ctx.usecase.ejecutar(r, frame.ctx.usuario.getId());
                JOptionPane.showMessageDialog(this,
                        "✅ Reserva creada y aprobada",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                frame.showScreen(MainFrame.LISTA);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
