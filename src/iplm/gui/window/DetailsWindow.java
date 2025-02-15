package iplm.gui.window;

import iplm.gui.button.AddButton;
import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.panel.search_panel.components.ActualLink;
import iplm.gui.panel.search_panel.components.UsedLink;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailsWindow extends AWindow {
    private DefaultTable m_table;
    private SearchBar m_search_bar;
    private SearchPanel m_search_panel;
    private AddButton m_add_detail_button;
    private Runnable m_update_search_panel_action;
    ArrayList<ASearchPanelStr> list_strings;

    public DetailsWindow() {
        build();
        afterBuild();
    }

    public DefaultTable getTable() { return m_table; }
    public SearchBar getSearchBar() { return m_search_bar; }
    public AddButton getAddDetailButton() { return m_add_detail_button; }

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
        list_strings = new ArrayList<>();

        m_search_panel = new SearchPanel();
        m_update_search_panel_action = () -> {
            list_strings.remove(this);
            m_search_panel.updateInfo(list_strings);
            m_search_panel.updateSize(m_search_bar.getWidth());
        };

        list_strings.add(new UsedLink("UsedLink", m_update_search_panel_action));
        list_strings.add(new ActualLink("ActualLink"));

        m_component_resized_callbacks.add(() -> m_search_panel.updateSize(m_search_bar.getWidth()));


        ArrayList<ASearchPanelStr> list_strings = new ArrayList<>();
        list_strings.add(new UsedLink("UsedLink", m_update_search_panel_action));
        list_strings.add(new ActualLink("ActualLink"));

        m_search_bar.addFocusAction(m_update_search_panel_action);
        m_search_bar.addUnfocusAction(() -> m_search_panel.updateInfo());
    }

    private void buildSearchBar() {
        m_search_bar = new SearchBar();
    }

    private void buildAddDetailButton() {
        m_add_detail_button = new AddButton();
        m_add_detail_button.setToolTipText("Добавить деталь");
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
