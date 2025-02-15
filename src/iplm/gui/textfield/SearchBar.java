package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.IconContainer;
import iplm.utility.FontUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SearchBar extends JTextField {
    private static final String SEARCH_STRING_PLACEHOLDER = "Поиск";
    private Color default_color;
    private JLabel search_icon;
    private Color default_back_color;
    private Color hover_background_color = new Color(250, 255, 250);
    private List<Runnable> actions;
    private List<Runnable> focus_actions;
    private List<Runnable> unfocus_actions;

    public SearchBar() {
        actions = new ArrayList<>();
        focus_actions = new ArrayList<>();
        unfocus_actions = new ArrayList<>();

        setText(SEARCH_STRING_PLACEHOLDER);

        default_color = getForeground();
        setForeground(Color.GRAY);

        default_back_color = getBackground();

        FontUtility.multResize(this, 1.5f);

        search_icon = new JLabel(Resources.getSVGIcon("search.svg").derive(16,16));

        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, new IconContainer(search_icon));
        putClientProperty(FlatClientProperties.STYLE, "arc: 35");

//        putClientProperty("FlatLaf.style", "arc: 45; iconTextGap: 5");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { if (!isFocusOwner()) setBackground(hover_background_color); }

            @Override
            public void mouseExited(MouseEvent e) { setBackground(default_back_color); }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText(SEARCH_STRING_PLACEHOLDER);
                    setForeground(Color.GRAY);
                }

                for (Runnable function : unfocus_actions) {
                    SwingUtilities.invokeLater(function);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().trim().equals(SEARCH_STRING_PLACEHOLDER)) {
                    setText("");
                    setForeground(default_color);
                }
                setBackground(default_back_color);

                for (Runnable function : focus_actions) {
                    SwingUtilities.invokeLater(function);
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
    public void addFocusAction(Runnable function) { focus_actions.add(function); }
    public void addUnfocusAction(Runnable function) { unfocus_actions.add(function); }

    public String getSearchText() {
        String result = getText().trim();
        if (result.equalsIgnoreCase(SEARCH_STRING_PLACEHOLDER)) result = "";
        return result;
    }
}
