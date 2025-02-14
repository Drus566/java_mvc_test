package iplm.gui.panel.search_panel.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanelStrType;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/*
    Иконка циферблата со стрелкой
    Последний соответствующий введенным символам текст запроса
    Можно удалить из истории
 */
public class RecentRequest extends AInfoStr {
    private static FlatSVGIcon close_svg_icon = Resources.getSVGIcon("close.svg").derive(24,24);
    private static FlatSVGIcon recent_svg_icon = Resources.getSVGIcon("time.svg").derive(24,24);
    private JButton close_btn;
    private JLabel recent_icon;

    RecentRequest() {
        type = SearchPanelStrType.INFO;

        close_btn = new JButton(close_svg_icon);
        close_btn.setOpaque(false);
        close_btn.setVisible(false);

        recent_icon = new JLabel(recent_svg_icon);
        recent_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, close_btn);
        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, recent_icon);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { close_btn.setVisible(true); }

            @Override
            public void mouseExited(MouseEvent e) { close_btn.setVisible(false); }
        });
    }
}
