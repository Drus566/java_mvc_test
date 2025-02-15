package iplm.gui.panel.search_panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
1. Недавние запросы - текст (иконка времени, кнопка удалить) -> при нажатии выдает список деталей в таблицу
 * Текст поиска
2. Выдача из бд - (иконка детали (если есть), -> при нажатии подгружает деталь и открывает окно информации о ней
 * Название детали (дец номер)
 * ID в бд
3. Выдача из бд, которую я уже открывал (иконка детали, кнопка удалить) -> при нажатии открывает деталь и открывает окно информации о ней
 * Название детали (дец номер)
 * ID в бд
 */

public class SearchPanel extends JPanel {
    private int current_height;
    private Color border_color = new Color(147, 179, 255);

    public SearchPanel() {
        setLayout(new MigLayout("inset 8 0 8 0, gap rel -2"));
        setBackground(Color.white);
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        setBorder(new FlatLineBorder(new Insets(0,0,0,0), border_color, 1, 30));
        setOpaque(false);
        setVisible(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                e.consume();
            }
        });
    }

    public void updateSize(int width) {
        SwingUtilities.invokeLater(() -> {
            Dimension size = new Dimension(width, current_height);
            setSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setPreferredSize(size);
            revalidate();
            repaint();
        });
    }

    public void updateInfo() { updateInfo(null); }

    public void updateInfo(ArrayList<ASearchPanelStr> fields) {
        SwingUtilities.invokeLater(() -> {
            removeAll();
            current_height = 0;
            if (fields == null || fields.isEmpty()) setVisible(false);
            else {
                for (ASearchPanelStr str : fields) {
                    if (str.type == SearchPanelStrType.DESCRIBE) {
                        add(str, "width 100%, gap 30 10 0 0, wrap");
                        current_height += str.getHeight() + str.getInsets().bottom;
                    }
                    else if (str.type == SearchPanelStrType.INFO) {
                        add(str, "width 100%, height 44!, gap 5 5, wrap");
                        current_height += str.getHeight() + str.getInsets().bottom;
                    }
                }
                if (current_height > 10) setVisible(true);
            }
            revalidate();
            repaint();
        });
    }
}
