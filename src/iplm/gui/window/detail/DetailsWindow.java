package iplm.gui.window.detail;

import iplm.data.history.RequestHistory;
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
import iplm.gui.textfield.SearchBar;
import iplm.gui.window.AWindow;
import iplm.managers.WindowsManager;
import iplm.mvc.views.DetailControlView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // EXAMPLE DATA FILLs
        m_table.addColumns(new ArrayList<>(Arrays.asList("ID", "Децимальный номер", "Наименование", "Описание")));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(0));
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

        m_search_bar.addEnterButtonAction(m_enter_btn_action);
    }

    private void buildAddDetailButton() {
        m_add_detail_button = new AddButton();
        m_add_detail_button.setToolTipText("Добавить деталь");
        m_add_detail_button.addAction(new Runnable() {
            @Override
            public void run() {
                WindowsManager.getInstance().showWindow("DetailControlWindow");
            }
        });
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
        JMenuItem params = new JMenuItem("Редактор параметров деталей");
        JMenuItem exit = new JMenuItem("Выход");
        menu.add(params);
        menu.add(exit);

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
        m_panel.add(m_table.getScrollPane(), "grow, push, span 3");
        m_layer = new JLayer<>(m_panel, new InterceptLayer());
    }

    @Override
    public void updateSearchPanel(ASearchPanelLine line) {
        StorageHistory.getInstance().remove(StorageHistoryType.DETAILS, line.ID);
        m_search_panel.removeLine(line.ID);
        m_search_panel.updateLines();
        m_search_panel.updateSize(m_search_bar.getWidth());
    }

    //    public void buildInterceptLayer() {
//        long all_events_mask = AWTEvent.MOUSE_EVENT_MASK |
//                AWTEvent.MOUSE_MOTION_EVENT_MASK |
//                AWTEvent.MOUSE_WHEEL_EVENT_MASK |
//                AWTEvent.KEY_EVENT_MASK |
//                AWTEvent.FOCUS_EVENT_MASK |
//                AWTEvent.ACTION_EVENT_MASK |
//                AWTEvent.INPUT_METHOD_EVENT_MASK |
//                AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK |
//                AWTEvent.ADJUSTMENT_EVENT_MASK |
//                AWTEvent.COMPONENT_EVENT_MASK |
//                AWTEvent.CONTAINER_EVENT_MASK |
//                AWTEvent.INVOCATION_EVENT_MASK |
//                AWTEvent.PAINT_EVENT_MASK |
//                AWTEvent.HIERARCHY_EVENT_MASK |
//                AWTEvent.ITEM_EVENT_MASK |
//                AWTEvent.TEXT_EVENT_MASK |
//                AWTEvent.WINDOW_EVENT_MASK |
//                AWTEvent.WINDOW_FOCUS_EVENT_MASK |
//                AWTEvent.WINDOW_STATE_EVENT_MASK;

//        InterceptLayer il = new InterceptLayer();
//        il.addPaintAction((g, c) -> {
////            System.out.println("C1: " + c.getClass().getSimpleName());
////            System.out.println("C2: " + c.getComponent(0).getClass().getSimpleName());
//
//            Rectangle local_table_rect = SwingUtilities.convertRectangle(m_table.getTable(), m_table.getTable().getVisibleRect(), m_details_filter_panel);
////            System.out.println("Local table rect: " + local_table_rect);
//
//            if (m_details_filter_panel.getVisibleRect().intersects(local_table_rect)) {
////                Rectangle intersection = m_details_filter_panel.getVisibleRect().intersection(local_table_rect);
////                System.out.println("Intersection: " + intersection);
//            }
//
//            c.paint(g);
//        });

//        il.addDispatchAction((e, l) -> {
//        });
//        m_layer = new JLayer<>(m_panel, new InterceptLayer());
//    }
}
