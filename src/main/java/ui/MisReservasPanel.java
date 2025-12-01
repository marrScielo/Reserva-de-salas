// src/main/java/app/ui/MisReservasPanel.java
package ui;

import domain.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MisReservasPanel extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Inicio","Fin","Estado"}, 0) {
        public boolean isCellEditable(int r, int c){ return false; }
    };
    private final JTable table = new JTable(model);

    public MisReservasPanel(MainFrame frame){
        setOpaque(false);
        setLayout(new BorderLayout(12,12));

        JPanel top = UIUtils.padded(8, 8, 0, 8, new BorderLayout());
        JLabel title = UIUtils.title("Mis Reservas");
        top.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton back = UIUtils.secondaryButton("← Volver");
        JButton refresh = UIUtils.primaryButton("Refrescar");
        actions.add(back); actions.add(refresh);
        top.add(actions, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // Estilo tabla
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        table.setFillsViewportHeight(true);

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        add(sp, BorderLayout.CENTER);

        back.addActionListener(e -> frame.showScreen(MainFrame.HOME));
        refresh.addActionListener(e -> reload(frame));
    }

    public void reload(MainFrame frame){
        model.setRowCount(0);
        List<Reserva> lista = frame.ctx.repo.findBySala(frame.ctx.sala.getId());
        for (Reserva r : lista) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getInicio().toString(),
                    r.getFin().toString(),
                    r.getEstado().name()
            });
        }
    }
}
