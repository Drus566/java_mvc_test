package iplm.gui.window.detail;

import iplm.data.history.RequestHistoryType;
import iplm.data.history.StorageHistory;
import iplm.data.history.StorageHistoryType;
import iplm.gui.button.AddButton;
import iplm.gui.button.FilterButton;
import iplm.gui.button.UpdateButton;
import iplm.gui.layer.intercept.InterceptLayer;
import iplm.gui.panel.details_filter.DetailsFilter;
import iplm.gui.panel.search_panel.components.ASearchPanelLine;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.panel.search_panel.components.button.ICloseSearchPanelLineListener;
import iplm.gui.table.DefaultTable;
import iplm.gui.table.detail.HighlightRenderer;
import iplm.gui.textfield.SearchBar;
import iplm.gui.window.AWindow;
import iplm.managers.WindowsManager;
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
    private UpdateButton m_update_button;
    private DetailsFilter m_details_filter_panel;

    private Runnable m_update_search_panel_action;
    private Runnable m_enter_btn_action;

    public DetailsWindow() {
        build();
        afterBuild();
    }

    public DefaultTable getTable() { return m_table; }
    public SearchBar getSearchBar() { return m_search_bar; }
    public AddButton getAddDetailButton() { return m_add_detail_button; }
    public SearchPanel getSearchPanel() { return m_search_panel; }
    public UpdateButton getUpdateButton() { return m_update_button; }

    public void clear() { }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        setTitle("Детали");

        m_panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_panel.requestFocus();
            }
        });
        m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildTable();
        buildSearchBar();
        buildSearchPanel();
        buildAddDetailButton();
        buildUpdateButton();
        buildDetailsFilter();
        buildMenuBar();
        arrangeComponents();
    }

    private void buildTable() {
        m_table = new DefaultTable();
        m_table.addColumns(new ArrayList<>(Arrays.asList("ID", "Децимальный номер", "Наименование", "Описание", "Цвет")));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(0));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(3));
        m_table.setRenderer(new HighlightRenderer(m_table.getTable()));
    }

    private void buildSearchPanel() {
        m_search_panel = new SearchPanel();

        m_update_search_panel_action = () -> {
            m_search_panel.updateLines();
            m_search_panel.updateSize(m_search_bar.getWidth());
        };

        m_component_resized_callbacks.add(() -> {
            m_search_panel.updateSize(m_search_bar.getWidth());
            m_frame.setPreferredSize(new Dimension(m_frame.getPreferredSize().width, m_frame.getPreferredSize().height));
        });

        m_search_bar.addFocusAction(m_update_search_panel_action);
        m_search_bar.addUnfocusAction(() -> m_search_panel.setVisible(false));
    }

    private void buildSearchBar() {
        m_search_bar = new SearchBar();

        m_filter_button = new FilterButton();
//        m_search_bar.addTrailingComponent(m_filter_button);

        m_enter_btn_action = () -> {
            String search_text = m_search_bar.getSearchText();
            if (search_text.isEmpty()) return;
            HashMap<String, Object> params = new HashMap<>();
            params.put("Query", search_text);
            StorageHistory.getInstance().add(StorageHistoryType.DETAILS, RequestHistoryType.RECENT_REQUEST, params);
            m_search_panel.setVisible(false);
//            StorageHistory.getInstance().saveHistory();
        };

        m_search_bar.addEnterButtonAction(m_enter_btn_action);
    }

    private void buildAddDetailButton() {
        m_add_detail_button = new AddButton();
        m_add_detail_button.setToolTipText("Добавить деталь");
    }

    private void buildUpdateButton() {
        m_update_button = new UpdateButton();
        m_update_button.setToolTipText("Обновить список деталей");
    }

    private void buildDetailsFilter() {
        m_details_filter_panel = new DetailsFilter();
        m_filter_button.addAction(() -> {
            if (m_details_filter_panel.isVisible()) m_details_filter_panel.setVisible(false);
            else m_details_filter_panel.setVisible(true);
        });
    }

    public void buildMenuBar() {
        m_menu_bar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        JMenuItem m1 = new JMenuItem("Управление типами параметров деталей");
        JMenuItem m2 = new JMenuItem("Управление наименованиями деталей");
        JMenuItem m3 = new JMenuItem("Управление деталью");
        JMenuItem m4 = new JMenuItem("Настройки");

        JMenuItem exit = new JMenuItem("Выход");
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        menu.add(m4);
        menu.add(exit);

        m1.addActionListener(e -> WindowsManager.getInstance().showWindow("DetailParameterTypeControlWindow"));
        m2.addActionListener(e -> WindowsManager.getInstance().showWindow("DetailNameControlWindow"));
        m3.addActionListener(e -> WindowsManager.getInstance().showWindow("DetailControlWindow"));
        m4.addActionListener(e -> WindowsManager.getInstance().showWindow("DetailSettingsWindow"));

        exit.addActionListener(e -> System.exit(0));
        m_menu_bar.add(menu);
    }

    private void arrangeComponents() {
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        width = width - width / 3;
        m_panel.add(m_details_filter_panel, "pos (search_bar.x+search_bar.w-" + m_details_filter_panel.getPreferredSize().getWidth() + "px) " + m_search_bar.getPreferredSize().getHeight() * 1.199 + "px");
        m_panel.add(m_search_panel, "pos search_bar.x " + m_search_bar.getPreferredSize().getHeight() * 1.199 + "px");

        m_panel.add(m_update_button, "split 3, al center");
        m_panel.add(m_search_bar, "id search_bar, height 40:pref:max, width min:pref:" + width + ", growx, al center");
        m_panel.add(m_add_detail_button, "al left, wrap");
        m_panel.add(m_table.getScrollPane(), "grow, push");
        m_layer = new JLayer<>(m_panel, new InterceptLayer());
    }

    @Override
    public void updateSearchPanel(ASearchPanelLine line) {
        StorageHistory.getInstance().remove(StorageHistoryType.DETAILS, line.ID);
        m_search_panel.removeLine(line.ID);
        m_search_panel.updateLines();
        m_search_panel.updateSize(m_search_bar.getWidth());
    }
}
