package iplm.gui.table;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DefaultTable {
    private JTable m_table;
    private JScrollPane m_scroll_pane;
    private DefaultTableModel m_model;

    private int last_row = -1;
    private long last_click_time = 0;
    private ArrayList<Runnable> double_click_action;

    public DefaultTable() {
        double_click_action = new ArrayList<>();

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

        m_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = m_table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    if (System.currentTimeMillis() - last_click_time < 300 && row == last_row) {
                        for (Runnable r : double_click_action) {
                            SwingUtilities.invokeLater(r);
                        }
                    }
                    else {
                        last_row = row;
                        last_click_time = System.currentTimeMillis();
                    }
                }
            }
        });
    }

    public JTable getTable() { return m_table; }
    public JScrollPane getScrollPane() { return m_scroll_pane; }
    public DefaultTableModel getTableModel() { return m_model; }

    public void addDoubleClickAction(Runnable action) {
        double_click_action.add(action);
    }

    public String getStringFromSelectedRowColumn(int column) {
        return (String)m_table.getModel().getValueAt(m_table.getSelectedRow(), column);
    }

    public void clear() { m_model.setRowCount(0); }

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
}
