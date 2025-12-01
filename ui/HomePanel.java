// src/main/java/app/ui/HomePanel.java
package app.ui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(MainFrame frame){
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx=0; c.gridy=0;
        c.anchor = GridBagConstraints.CENTER;

        JPanel card = UIUtils.padded(24, 24, 24, 24, new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Theme.BORDER));

        GridBagConstraints k = new GridBagConstraints();
        k.insets = new Insets(12,12,12,12);
        k.gridx=0; k.gridy=0; k.anchor=GridBagConstraints.CENTER;

        JLabel title = UIUtils.title("Reserva de Salas");
        card.add(title, k);

        k.gridy++;
        JLabel subtitle = UIUtils.subtitle("Automatiza tus reservas, evita conflictos y gana tiempo.");
        card.add(subtitle, k);

        k.gridy++;
        JButton btnNueva = UIUtils.primaryButton("➕ Nueva reserva");
        card.add(btnNueva, k);

        k.gridy++;
        JButton btnMis = UIUtils.secondaryButton("📋 Mis reservas");
        card.add(btnMis, k);

        add(card, c);

        btnNueva.addActionListener(e -> frame.showScreen(MainFrame.NUEVA));
        btnMis.addActionListener(e -> frame.showScreen(MainFrame.LISTA));
    }
}
