package iplm.gui.panel.search_panel.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanelStrType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/*
    Иконка сущности БД (например иконка детали)
    Ссылки (запросы) к конкретным объектам БД по которым уже проходил.
    Можно удалить из истории
 */
public class UsedLink extends AInfoStr {
    private static FlatSVGIcon close_svg_icon = Resources.getSVGIcon("close.svg").derive(24,24);
    private FlatSVGIcon entity_svg_icon = Resources.getSVGIcon("detail.svg").derive(24,24);
    private JButton close_btn;
    private JLabel entity_icon;

    public UsedLink(String string) {
        setText(string);
        type = SearchPanelStrType.INFO;

        close_btn = new JButton(close_svg_icon);
        close_btn.setOpaque(false);
        close_btn.setVisible(false);

        entity_icon = new JLabel(entity_svg_icon);
        entity_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, close_btn);
        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, entity_icon);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { close_btn.setVisible(true); }

            @Override
            public void mouseExited(MouseEvent e) { close_btn.setVisible(false); }
        });
    }
}
