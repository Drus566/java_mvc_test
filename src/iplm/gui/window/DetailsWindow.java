package iplm.gui.window;

import iplm.data.history.RequestHistory;
import iplm.data.history.RequestHistoryType;
import iplm.data.history.StorageHistory;
import iplm.data.history.StorageHistoryType;
import iplm.gui.button.AddButton;
import iplm.gui.button.FilterButton;
import iplm.gui.panel.details_filter.DetailsFilter;
import iplm.gui.panel.search_panel.components.ASearchPanelLine;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.panel.search_panel.components.button.ICloseSearchPanelLineListener;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DetailsWindow extends AWindow implements ICloseSearchPanelLineListener {
    private DefaultTable m_table;
    private SearchBar m_search_bar;
    private SearchPanel m_search_panel;
    private AddButton m_add_detail_button;
    private FilterButton m_filter_button;
    private DetailsFilter m_details_filter_panel;

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
        m_panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_panel.requestFocus();
            }
        });
        buildTable();
        buildSearchBar();
        buildSearchPanel();
        buildAddDetailButton();
        buildDetailsFilter();
        arrangeComponents();
    }

    private void buildTable() {
        m_table = new DefaultTable();
//        m_table.addTableValueChangeAction(() -> {
//            m_details_filter_panel.revalidate();
//            m_details_filter_panel.repaint();
//        });
        m_table.addColumns(new ArrayList<>(Arrays.asList("Name", "Type", "Size")));
        for (int i = 0; i < 100; i++) {
            m_table.addLine(new ArrayList<>(Arrays.asList("Document.txt", "Text File", "15 KB")));
            m_table.addLine(new ArrayList<>(Arrays.asList("Image.jpg", "Image", "2 MB")));
            m_table.addLine(new ArrayList<>(Arrays.asList("Spreadsheet.xlsx", "Excel", "500 KB")));
        }
    }

    private void buildSearchPanel() {
        m_search_panel = new SearchPanel();

        m_update_search_panel_action = () -> {
            m_search_panel.updateLines();
            m_search_panel.updateSize(m_search_bar.getWidth());
        };

        m_component_resized_callbacks.add(() -> {
            m_search_panel.updateSize(m_search_bar.getWidth());
        });

        m_search_bar.addFocusAction(m_update_search_panel_action);
        m_search_bar.addUnfocusAction(() -> m_search_panel.setVisible(false));
    }

    private void buildSearchBar() {
        m_search_bar = new SearchBar();

        m_filter_button = new FilterButton();
        m_search_bar.addTrailingComponent(m_filter_button);

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

    private void buildDetailsFilter() {
        m_details_filter_panel = new DetailsFilter();
        m_filter_button.addAction(() -> {
            if (m_details_filter_panel.isVisible()) m_details_filter_panel.setVisible(false);
            else m_details_filter_panel.setVisible(true);
        });
    }

    private void arrangeComponents() {
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        width = width - width / 4;
        m_panel.add(m_details_filter_panel, "pos (search_bar.x+search_bar.w-" + m_details_filter_panel.getPreferredSize().getWidth() + "px) " + m_search_bar.getPreferredSize().getHeight() * 1.199 + "px");
        m_panel.add(m_search_panel, "pos search_bar.x " + m_search_bar.getPreferredSize().getHeight() * 1.199 + "px");

//        m_panel.add(m_search_bar, "id search_bar, height 40:pref:max, width min:pref:max, growx, split 2");
        m_panel.add(m_search_bar, "id search_bar, height 40:pref:max, width min:pref:" + width + ", growx, split 2, al center");
        m_panel.add(m_add_detail_button, "al left, wrap");
        m_panel.add(m_table.getScrollPane(), "grow, push");
    }

    @Override
    public void updateSearchPanel(ASearchPanelLine line) {
        StorageHistory.getInstance().remove(StorageHistoryType.DETAILS, line.ID);
        m_search_panel.removeLine(line.ID);
        m_search_panel.updateLines();
        m_search_panel.updateSize(m_search_bar.getWidth());
    }
}
