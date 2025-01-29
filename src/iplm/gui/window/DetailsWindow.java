package iplm.gui.window;

import iplm.gui.table.DefaultTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailsWindow extends AWindow {
    private DefaultTable m_table;

    public DetailsWindow() {
        build();
        afterBuild();
    }

    public DefaultTable getTable() { return m_table; };

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("insets 10"));

        buildTable();

        m_panel.add(m_table.getScrollPane());
        m_panel.setMinimumSize(new Dimension(300, 400));
    }

    private void buildTable() {
        m_table = new DefaultTable();
        m_table.addColumns(new ArrayList<>(Arrays.asList("Name", "Type", "Size")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Document.txt", "Text File", "15 KB")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Image.jpg", "Image", "2 MB")));
        m_table.addLine(new ArrayList<>(Arrays.asList("Spreadsheet.xlsx", "Excel", "500 KB")));
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
