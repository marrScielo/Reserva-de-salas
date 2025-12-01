
package app.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UIUtils {
    private UIUtils(){}

    public static JPanel padded(int top, int left, int bottom, int right, LayoutManager lm){
        JPanel p = new JPanel(lm);
        p.setBorder(new EmptyBorder(top, left, bottom, right));
        return p;
    }

    public static JButton primaryButton(String text){
        JButton b = new JButton(text);
        b.setBackground(Theme.PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
        b.setPreferredSize(new Dimension(200, 40));
        return b;
    }

    public static JButton secondaryButton(String text){
        JButton b = new JButton(text);
        b.setBackground(Color.WHITE);
        b.setForeground(Theme.ACCENT);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
        b.setPreferredSize(new Dimension(200, 40));
        b.setBorder(BorderFactory.createLineBorder(Theme.ACCENT, 2, true));
        return b;
    }

    public static JLabel title(String text){
        JLabel l = new JLabel(text);
        l.setFont(Theme.h1(l));
        l.setForeground(Theme.ACCENT);
        return l;
    }

    public static JLabel subtitle(String text){
        JLabel l = new JLabel(text);
        l.setFont(Theme.body(l));
        l.setForeground(new Color(90, 90, 90));
        return l;
    }
}
