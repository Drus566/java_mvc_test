package iplm.gui.panel.search_panel.components.types;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.IconContainer;
import iplm.gui.panel.search_panel.components.AInfoLine;
import iplm.gui.panel.search_panel.components.ASearchPanelLine;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    Иконка поиск.
    Запросы, предлагаемые первые 10 вариантов по индексу, которые соответствую введенным символам.
 */
public class ActualLink extends AInfoLine {
    private static FlatSVGIcon search_svg_icon = Resources.getSVGIcon("search.svg").derive(16,16);
    private JLabel search_icon;

    public ActualLink(String string) {
        ID = -1;
        setText(string);

        type = ASearchPanelLine.Type.INFO;
        search_icon = new JLabel(search_svg_icon);
        search_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, new IconContainer(search_icon));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SwingUtilities.invokeLater(() -> setBackground(hover_color));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SwingUtilities.invokeLater(() -> setBackground(background_color));
            }
        });
    }
}
