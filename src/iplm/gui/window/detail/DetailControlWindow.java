package iplm.gui.window.detail;

import iplm.gui.button.AddButton;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailControlWindow extends AWindow {

    public DetailControlWindow() {
        build();
        afterBuild();
    }

//    public DefaultTable getTable() { return m_table; }
//    public SearchBar getSearchBar() { return m_search_bar; }
//    public AddButton getAddDetailButton() { return m_add_detail_button; }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
//        buildTable();
//        buildSearchBar();
//        buildSearchPanel();
//        buildAddDetailButton();
//        buildDetailsFilter();
//        buildInterceptLayer();
//        arrangeComponents();
    }
}
