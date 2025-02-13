package iplm.gui.window;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.Resources;
import iplm.gui.button.AddDetail;
import iplm.gui.panel.SearchPanel;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import iplm.utility.FontUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailsWindow extends AWindow {
    private DefaultTable m_table;
    private SearchBar m_search_bar;
    private SearchPanel m_search_panel;
    private AddDetail m_add_detail_button;

    public DetailsWindow() {
        build();
        afterBuild();
    }

    public DefaultTable getTable() { return m_table; }
    public SearchBar getSearchBar() { return m_search_bar; }
    public AddDetail getAddDetailButton() { return m_add_detail_button; }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        buildTable();
        buildSearchBar();
        buildSearchPanel();
        buildAddDetailButton();
        arrangeComponents();
    }

    private void buildTable() {
        m_table = new DefaultTable();
        m_table.addColumns(new ArrayList<>(Arrays.asList("Name", "Type", "Size")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Document.txt", "Text File", "15 KB")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Image.jpg", "Image", "2 MB")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Spreadsheet.xlsx", "Excel", "500 KB")));
    }

    private void buildSearchPanel() {
        m_search_panel = new SearchPanel();

        m_component_resized_callbacks.add(() -> {
            int h = m_search_panel.getHeight();
            m_search_panel.setSize(m_search_bar.getWidth(), h);
            m_search_panel.setMinimumSize(new Dimension(m_search_bar.getWidth(), h));
            m_search_panel.setMaximumSize(new Dimension(m_search_bar.getWidth(), h));
            m_search_panel.setPreferredSize(new Dimension(m_search_bar.getWidth(), h));

            m_search_panel.repaint();
            m_search_panel.revalidate();
        });

        m_search_bar.addFocusAction(() -> m_search_panel.setVisible(true));
        m_search_bar.addUnfocusAction(() -> m_search_panel.setVisible(false));
    }

    private void buildSearchBar() {
        m_search_bar = new SearchBar();
    }

    private void buildAddDetailButton() {
        m_add_detail_button = new AddDetail();
    }

    private void arrangeComponents() {
        m_panel.add(m_search_bar, "cell 0 0, height 40:pref:max, growx, pushx");
        m_panel.add(m_search_panel, "pos 0al " + m_search_bar.getPreferredSize().getHeight() * 1.199 + "px");
        m_panel.add(m_add_detail_button, "cell 1 0");
        m_panel.add(m_table.getScrollPane(), "cell 0 1 2, grow, push");
        m_panel.setMinimumSize(new Dimension(300, 0));
    }

//    String[] columnNames = {"Name", "Type", "Size"};
//
//    // Данные для таблицы
//    Object[][] data = {
//            {"Document.txt", "Text File", "15 KB"},
//            {"Image.jpg", "Image", "2 MB"},
//            {"Spreadsheet.xlsx", "Excel", "500 KB"}
//    };
//
//    // Создание таблицы
//    JTable table = new JTable(data, columnNames);
//
//    // Добавление таблицы в ScrollPane
//    JScrollPane scrollPane = new JScrollPane(table);
//
//    // Настройка окна
//    setTitle("Simple JTable Example");
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    setSize(400, 300);
}
