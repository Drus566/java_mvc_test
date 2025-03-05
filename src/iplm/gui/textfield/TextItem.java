package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.gui.button.DeleteButton;
import iplm.gui.panel.item_list_panel.IItem;
import iplm.gui.panel.item_list_panel.IItemListener;
import iplm.utility.ColorUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextItem extends JTextField implements IItem {
    private static final int MAX_SYMBOL_STR = 30;

    private IItem current_item;

    private DeleteButton m_delete_btn;
    private IItemListener m_listener;

    private Color base_color = Color.white;
    private Color hover_color = Color.lightGray;

    public TextItem(String text, String id, int height) {
        current_item = this;
        setToolTipText(text);

        setText(text);
        setBorder(null);
        setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));

        m_delete_btn = new DeleteButton(16,16);
        setEditable(false);
        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, m_delete_btn);
        putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(base_color));
        m_delete_btn.setBorder(new EmptyBorder(0,10,0,12));

        setMinimumSize(new Dimension(100, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_listener.onPress(current_item);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hover_color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(base_color);
            }
        });
    }

    public int getWidthLabel() { return this.getWidth() - m_delete_btn.getWidth(); }

    public void addDeleteAction(Runnable action) { m_delete_btn.addAction(action); }

    @Override
    public boolean contain(String text) { return getText().equalsIgnoreCase(text); }

    @Override
    public void addItemListener(IItemListener listener) { this.m_listener = listener; }

    @Override
    public String getPayload() { return getText(); }

    @Override
    public JComponent getComponent() { return this; }
}
