package iplm.gui.panel.search_panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.data.history.RequestHistoryType;
import iplm.gui.panel.search_panel.components.*;
import iplm.gui.panel.search_panel.components.button.ICloseSearchPanelLineListener;
import iplm.gui.panel.search_panel.components.types.*;
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
    private ArrayList<ASearchPanelLine> search_panel_lines;

    public SearchPanel() {
        setLayout(new MigLayout("inset 8 0 8 0, gap rel -2"));
        setBackground(Color.white);
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        search_panel_lines = new ArrayList<>();
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

    public int getLinesCount() { return search_panel_lines.size(); }

    public void addHistoryLine(int id, String text, RequestHistoryType rh_type, ICloseSearchPanelLineListener listener) {
        switch (rh_type) {
            case RECENT_REQUEST:
                search_panel_lines.add(new RecentRequest(id, text, listener));
                break;
            case USED_LINK:
                search_panel_lines.add(new UsedLink(id, text, listener));
                break;
        }
    }

    public void addDescribeLine(DescriptionLine.Type type) {
        search_panel_lines.add(new DescriptionLine(type.getDescription()));
    }

    public void addActualLine(String text) {
        search_panel_lines.add(new ActualLink(text));
    }

    public void removeLine(int id) {
        for (int i = 0; i < search_panel_lines.size(); i++) {
            if (search_panel_lines.get(i).ID == id) {
                search_panel_lines.remove(i);
                break;
            }
        }
    }

    public void removeLines() {
        search_panel_lines.clear();
    }

    public void updateSize(int width) {
        Dimension size = new Dimension(width, current_height);
        setSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        revalidate();
        repaint();
    }

    public void updateLines() {
        removeAll();
        current_height = 0;
        int line_info_height = 44;
        int line_info_offset = 4;
        int panel_insets = 16;

        if (search_panel_lines == null || search_panel_lines.isEmpty()) setVisible(false);
        else {
            for (ASearchPanelLine line : search_panel_lines) {
                if (line.type == ASearchPanelLine.Type.DESCRIBE) {
                    add(line, "width 100%, gap 30 10 0 0, wrap");
                    current_height += line.getHeight() + line.getInsets().bottom;
                }
                else if (line.type == ASearchPanelLine.Type.INFO) {
                    add(line, "width 100%, height " + line_info_height + "!, gap 5 5, wrap");
                    current_height += line.getPreferredSize().getHeight() + line.getInsets().bottom + line.getInsets().top + line_info_offset;
                }
            }
            current_height += panel_insets;
            revalidate();
            repaint();

            setVisible(true);
        }
    }
}
