package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String HOME = "HOME";
    public static final String NUEVA = "NUEVA";
    public static final String LISTA = "LISTA";

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    public final AppContext ctx = new AppContext();

    public MainFrame() {
        super("Reserva de Salas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);

        //Activar Look & Feel moderno (Nimbus)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        //  Barra superior (título)
        JPanel topBar = UIUtils.padded(10, 16, 10, 16, new BorderLayout());
        topBar.setBackground(Color.WHITE);

        JLabel brand = new JLabel("Reserva de Salas");
        brand.setFont(Theme.h2(brand));
        brand.setForeground(Theme.ACCENT);
        topBar.add(brand, BorderLayout.WEST);

        //  Pantallas del sistema
        HomePanel home = new HomePanel(this);
        NuevaReservaPanel nueva = new NuevaReservaPanel(this);
        MisReservasPanel lista = new MisReservasPanel(this);

        root.add(home, HOME);
        root.add(nueva, NUEVA);
        root.add(lista, LISTA);

        // Fondo general
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BG_SOFT);
        wrapper.add(topBar, BorderLayout.NORTH);

        //  Panel interno con padding (corrección del error)
        JPanel inner = UIUtils.padded(16, 16, 16, 16, new BorderLayout());
        inner.add(root, BorderLayout.CENTER);
        wrapper.add(inner, BorderLayout.CENTER);

        setContentPane(wrapper);
        showScreen(HOME);
    }

    // Control de navegación entre pantallas
    public void showScreen(String name) {
        if (LISTA.equals(name)) {
            Component comp = root.getComponent(2); // MisReservasPanel agregado en tercer lugar
            if (comp instanceof MisReservasPanel) {
                ((MisReservasPanel) comp).reload(this);
            }
        }
        cards.show(root, name);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
