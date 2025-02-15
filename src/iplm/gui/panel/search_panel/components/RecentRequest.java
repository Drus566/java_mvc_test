package iplm.gui.panel.search_panel.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.IconContainer;
import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanelStrType;
import iplm.gui.panel.search_panel.components.button.CloseButton;

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
    private static FlatSVGIcon recent_svg_icon = Resources.getSVGIcon("time.svg").derive(16,16);
    private CloseButton close_btn;
    private IconContainer close_btn_container;
    private JLabel recent_icon;

    RecentRequest() {
        type = SearchPanelStrType.INFO;

        close_btn = new CloseButton();
        close_btn.setVisible(false);
        close_btn_container = new IconContainer(close_btn);

        recent_icon = new JLabel(recent_svg_icon);
        recent_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, close_btn_container);
        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new IconContainer(recent_icon));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    setBackground(hover_color);
                    close_btn.setVisible(true);
                });
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (close_btn_container.pointInner(e.getPoint())) {
                        setBackground(background_color);
                        close_btn.setVisible(false);
                    }
                });
            }
        });
    }
}
