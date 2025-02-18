package iplm.gui.window;

import iplm.data.history.RequestHistory;
import iplm.data.history.RequestHistoryType;
import iplm.data.history.StorageHistory;
import iplm.data.history.StorageHistoryType;
import iplm.gui.button.AddButton;
import iplm.gui.panel.search_panel.ASearchPanelLine;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.panel.search_panel.components.button.ICloseSearchPanelLineListener;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DetailsWindow extends AWindow implements ICloseSearchPanelLineListener {
    private DefaultTable m_table;
    private SearchBar m_search_bar;
    private SearchPanel m_search_panel;
    private AddButton m_add_detail_button;
    private Runnable m_update_search_panel_action;
    private Runnable m_enter_btn_action;
    private Runnable m_tap_action;

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
        m_search_panel = new SearchPanel();

        m_update_search_panel_action = () -> {
            m_search_panel.updateLines();
            m_search_panel.updateSize(m_search_bar.getWidth());
        };

        m_component_resized_callbacks.add(() -> m_search_panel.updateSize(m_search_bar.getWidth()));

        m_search_bar.addFocusAction(m_update_search_panel_action);
        m_search_bar.addUnfocusAction(() -> m_search_panel.setVisible(false));
    }

    private void buildSearchBar() {
        m_search_bar = new SearchBar();

        m_enter_btn_action = () -> {
            String search_text = m_search_bar.getSearchText();
            if (search_text.isEmpty()) return;
            HashMap<String, Object> params = new HashMap<>();
            params.put("Query", search_text);
            StorageHistory.getInstance().add(StorageHistoryType.DETAILS, RequestHistoryType.RECENT_REQUEST, params);
            m_search_panel.setVisible(false);
//            StorageHistory.getInstance().saveHistory();
        };

        m_tap_action = () -> {
            ArrayList<RequestHistory> request_history = StorageHistory.getInstance().search(StorageHistoryType.DETAILS, m_search_bar.getSearchText());
            m_search_panel.removeLines();

            if (request_history != null) {
                for (int i = 0; i < request_history.size(); i++) {
                    RequestHistory rh = request_history.get(i);
                    m_search_panel.addHistoryLine(rh.id, (String) rh.params.get("Query"), rh.type, this);
                }
            }

            // add action link

            m_search_panel.updateLines();
            m_search_panel.updateSize(m_search_bar.getWidth());
        };

        m_search_bar.addTapAction(m_tap_action);
        m_search_bar.addEnterButtonAction(m_enter_btn_action);
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

    @Override
    public void updateSearchPanel(ASearchPanelLine line) {
        StorageHistory.getInstance().remove(StorageHistoryType.DETAILS, line.ID);
        m_search_panel.removeLine(line.ID);
        m_search_panel.updateLines();
        m_search_panel.updateSize(m_search_bar.getWidth());
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
