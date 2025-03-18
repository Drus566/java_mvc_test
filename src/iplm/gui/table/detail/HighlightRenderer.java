package iplm.gui.table.detail;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HighlightRenderer extends DefaultTableCellRenderer {
//        private int[] center_columns = { 0, 3, 4 };
//        private int[] left_columns = { 1, 2 };
    private JTable m_table;
    private int COLOR_COLUMN = 5;

    private Color highlight_color = new Color(0xFFB8B8);
    private Color select_color = UIManager.getColor("Table.selectionBackground");
    private Color select_inactive_color = UIManager.getColor("Table.selectionInactiveBackground");
    private Color default_color;

    public HighlightRenderer(JTable table) {
        m_table = table;
        default_color = m_table.getBackground();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean is_selected, boolean has_focus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, is_selected, has_focus, row, column);
        String color = (String) table.getModel().getValueAt(row, COLOR_COLUMN);
        if (color.equalsIgnoreCase(Boolean.TRUE.toString())) {
            cell.setBackground(default_color);
            if (is_selected) {
                if (m_table.hasFocus()) cell.setBackground(select_color);
                else cell.setBackground(select_inactive_color);
            }
            else if (cell.getBackground() != highlight_color) {
                cell.setBackground(highlight_color);
            }
        }
        else {
            cell.setBackground(default_color);
            if (is_selected) {
                if (m_table.hasFocus()) cell.setBackground(select_color);
                else cell.setBackground(select_inactive_color);
            }
        }
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, is_selected, has_focus, row, column);
        label.setToolTipText(value != null ? value.toString() : "");
        return cell;
    }
}
