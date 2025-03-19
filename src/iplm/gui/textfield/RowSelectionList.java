package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.Resources;
import iplm.gui.panel.IconContainer;
import iplm.gui.popup.IRowListPopupListener;
import iplm.gui.popup.RowListPopup;
import iplm.utility.ColorUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class RowSelectionList extends JTextField implements IRowListPopupListener {
    private HashSet<String> data;
    private RowListPopup popup;
    private String selected_row;
    private boolean select = false;
    private boolean method = false;
    private ArrayList<Runnable> callbacks;

    public void setValue(String value) {
        method = true;
        data.add(value);
        setText(value);
        selected_row = value;
        method = false;
    }

    public void setMaximumVisibleRows(int count) { popup.setMaximumVisibleRows(count); }

    public void addCallback(Runnable r) { callbacks.add(r); }

    public String getValue() { return getSelectedRow(); }

    public RowSelectionList() {
        JLabel search_icon = new JLabel(Resources.getSVGIcon("search.svg").derive(16,16));
        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, new IconContainer(30,search_icon.getHeight(),search_icon));
        callbacks = new ArrayList<>();
        data = new HashSet<>();
        popup = new RowListPopup();
        popup.addListener(this);
        buildActions();
        putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(Color.white));
    }

    public boolean isExistsValue(String value) {
        boolean result = false;
        for (String d : data) {
            if (d.equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void setEnable(boolean flag) {
        setEditable(flag);
        if (!flag) hidePopup();
    }

    private void buildActions() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { typeAction(); }

            @Override
            public void removeUpdate(DocumentEvent e) { typeAction(); }

            @Override
            public void changedUpdate(DocumentEvent e) { }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isEditable()) return;
                if (popup.getItemsCount() < data.size()) popup.updateItems(data);
                showPopup();
//                if (popup.isVisible()) hidePopup();
//                else showPopup();
            }
        });
    }

    private void typeAction() {
        if (select || method) return;

        SwingUtilities.invokeLater(() -> {
            String text = getText().trim();
            boolean is_empty = text.isEmpty();

            popup.removeItems();

            if (is_empty) {
                for (String s : data) {
                    popup.addItem(s);
                }
            }
            else {
                for (String s : data) {
                    if (s.toLowerCase().contains(text.toLowerCase())) popup.addItem(s);
                }
            }

            if (isEditable()) showPopup();
        });
    }

    public void updateData(HashSet<String> data) {
        this.data.clear();
        for (String s : data) {
            this.data.add(s);
        }
        setValue(getSelectedRow());
        if (popup.isVisible()) showPopup();
    }

    public void updateData(ArrayList<String> data) {
        this.data.clear();
        for (String s : data) {
            this.data.add(s);
        }
        setValue(getSelectedRow());
        if (popup.isVisible()) showPopup();
    }

    public void addData(String data) { this.data.add(data); }

    public void removeData(String data) { this.data.remove(data); }

    public void updatePopupData() { popup.updateItems(this.data); }

    public void changeData(String old, String name) {
        removeData(old);
        addData(name);
    }

    public boolean isDataExists(String data) {
        boolean result = false;
        for (String s : this.data) {
            if (data.equals(s)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void clearData() {
        this.data.clear();
        if (popup.isVisible()) {
            showPopup();
        }
    }

    public String getSelectedRow() { return (selected_row == null) ? "" : selected_row; }

    public void showPopup() { popup.show(this, 0, this.getHeight()); }
    public void hidePopup() { popup.setVisible(false); }

    @Override
    public void clickPopupRowAction(String text) {
        select = true;
        selected_row = text;
        hidePopup();
        setText(text);
        setCaretPosition(0);
        select = false;

        for (Runnable r : callbacks) {
            r.run();
        }
    }
}

//    RowListPopup spm = new RowListPopup("HUI");
////        spm.setPopupSize(600, m_detail_decimal_number_input.getHeight());
////        spm.setPreferredSize(new Dimension(600, m_detail_decimal_number_input.getHeight()));
//
//        m_detail_decimal_number_input.getDocument().addDocumentListener(new DocumentListener() {
//@Override
//public void insertUpdate(DocumentEvent e) {
//        SwingUtilities.invokeLater(() -> {
//        System.out.println(m_detail_decimal_number_input.getText());
//        spm.addItem(m_detail_decimal_number_input.getText());
//        spm.show(m_detail_decimal_number_input, 0, m_detail_decimal_number_input.getHeight());
//
//        });
//        }
//
//@Override
//public void removeUpdate(DocumentEvent e) {
//        SwingUtilities.invokeLater(() -> {
//        spm.remove(0);
//        spm.show(m_detail_decimal_number_input, 0, m_detail_decimal_number_input.getHeight());
//        });
//        }
//
//@Override
//public void changedUpdate(DocumentEvent e) {
//
//        }
//        });
//
//        m_detail_decimal_number_input.addMouseListener(new MouseAdapter() {
//@Override
//public void mouseClicked(MouseEvent e) {
//        super.mouseClicked(e);
////                for (int i = 0; i < 20; i++) {
////                    spm.add("itemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitemitem " + i);
////                }
//        spm.show(m_detail_decimal_number_input, 0, m_detail_decimal_number_input.getHeight());
//        }
//        });
//
