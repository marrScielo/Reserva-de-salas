
package app.ui;

import java.awt.*;

public final class Theme {
    private Theme(){}

    // Colores base
    public static final Color PRIMARY = new Color(0x29, 0x35, 0x4B);   
    public static final Color ACCENT  = new Color(0x2A, 0x36, 0x4D);  
    public static final Color BG_SOFT = new Color(0xF4, 0xF6, 0xFA);   
    public static final Color BORDER  = new Color(0xDD, 0xDD, 0xDD);

    // Fuentes
    public static Font h1(Component c){ return c.getFont().deriveFont(Font.BOLD, 22f); }
    public static Font h2(Component c){ return c.getFont().deriveFont(Font.BOLD, 18f); }
    public static Font body(Component c){ return c.getFont().deriveFont(14f); }
}
