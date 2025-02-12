package iplm.utility;

import javax.swing.*;
import java.awt.*;

public class FontUtility {
    public static String getDefaultFontName() {
        JLabel label = new JLabel();
        Font current_font = label.getFont();
        String font_name = current_font.getFontName();
//        String font_family = current_font.getFamily();
        return font_name;
    }

    public static void resize(JComponent component, int size) {
        Font currentFont = component.getFont();
        if (currentFont != null) {
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), size);
            component.setFont(newFont);
        }
        else System.out.println("Компонент не имеет установленного шрифта.");
    }

    public static void multResize(JComponent component, float multiply) {
        Font current_font = component.getFont();
        if (current_font != null) {
            int size = current_font.getSize();
            size *= multiply;
            Font newFont = new Font(current_font.getName(), current_font.getStyle(), size);
            component.setFont(newFont);
        }
        else System.out.println("Компонент не имеет установленного шрифта.");
    }
}
