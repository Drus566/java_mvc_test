package iplm.gui.panel.search_panel.components.types;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.IconContainer;
import iplm.gui.panel.search_panel.components.AInfoLine;
import iplm.gui.panel.search_panel.components.ASearchPanelLine;
import iplm.gui.panel.search_panel.components.button.CloseButton;
import iplm.gui.panel.search_panel.components.button.ICloseSearchPanelLineListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    Иконка циферблата со стрелкой
    Последний соответствующий введенным символам текст запроса
    Можно удалить из истории
 */
public class RecentRequest extends AInfoLine {
    private static FlatSVGIcon recent_svg_icon = Resources.getSVGIcon("time.svg").derive(16,16);
    private CloseButton close_btn;
    private IconContainer close_btn_container;
    private JLabel recent_icon;

    public RecentRequest(int id, String string, ICloseSearchPanelLineListener close_btn_listener) {
        ID = id;
        setText(string);
        type = ASearchPanelLine.Type.INFO;

        close_btn = new CloseButton();
        close_btn_container = new IconContainer(close_btn);
        close_btn.addAction(() -> close_btn_listener.updateSearchPanel(this));
        close_btn.setVisible(false);

        recent_icon = new JLabel(recent_svg_icon);
        recent_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, close_btn_container);
        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, new IconContainer(recent_icon));

        recent_icon.setToolTipText("Недавний запрос");

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
                    if (!close_btn_container.pointInner(e.getPoint())) {
                        setBackground(background_color);
                        close_btn.setVisible(false);
                    }
                });
            }

        });
    }
}
