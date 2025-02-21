package iplm.gui.table;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DefaultTable {
    private JTable m_table;
    private JScrollPane m_scroll_pane;
    private DefaultTableModel m_model;

    public DefaultTable() {
        m_table = new JTable() {
          @Override
          public boolean isCellEditable(int rowIndex, int columnIndex) {
              return false;
          }
        };

        m_model = new DefaultTableModel();
        m_table.setModel(m_model);
        m_scroll_pane = new JScrollPane(m_table);
        m_scroll_pane.putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        m_table.getTableHeader().setReorderingAllowed(false);
        m_table.getTableHeader().setFocusable(false);
//        m_table.setFocusable(false);
    }

    public JTable getTable() { return m_table; }
    public JScrollPane getScrollPane() { return m_scroll_pane; }
    public DefaultTableModel getTableModel() { return m_model; }

    public void addColumns(List<String> columns) {
        for (String column : columns) {
            m_model.addColumn(column);
        }
    }

    public void addLine(List<String> line) {
        m_model.addRow(line.toArray());
    }

    public void addLines(List<List<String>> many_lines) {
        for (List<String> line : many_lines) {
            addLine(line);
        }
    }

    public void addMouseClickAction(Runnable action) {
    }
}
