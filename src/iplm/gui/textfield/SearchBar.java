package iplm.gui.textfield;

import iplm.utility.FontUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchBar extends JTextField {
    private static final String SEARCH_STRING_PLACEHOLDER = "Поиск";
    private Color default_color;
    private List<Runnable> actions;

    public SearchBar() {
        actions = new ArrayList<>();
        setText(SEARCH_STRING_PLACEHOLDER);
        default_color = getForeground();
        setForeground(Color.GRAY);
        FontUtility.multResize(this, 1.5f);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText(SEARCH_STRING_PLACEHOLDER);
                    setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().trim().equals(SEARCH_STRING_PLACEHOLDER)) {
                    setText("");
                    setForeground(default_color);
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    super.keyPressed(e);
                    for (Runnable function : actions) {
                        function.run();
                    }
                }
            }
        });
    }

    public void addKeyboardEnterAction(Runnable function) { actions.add(function); }

    public String getSearchText() {
        String result = getText().trim();
        if (result.equalsIgnoreCase(SEARCH_STRING_PLACEHOLDER)) result = "";
        return result;
    }
}
