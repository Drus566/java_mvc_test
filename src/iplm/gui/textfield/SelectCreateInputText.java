package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.gui.button.AddButton;
import iplm.gui.button.DropdownButton;
import iplm.gui.panel.item_list_panel.IItemListPanelListener;
import iplm.gui.panel.item_list_panel.ItemListPanel;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* Позволяет выбирать из списка, добавлять в список и удалить из списка Items (например строки)
 */
public class SelectCreateInputText extends JTextField implements IItemListPanelListener {
    private JTextField own;
    // Кнопка добавить в список
    private AddButton m_add_btn;
    // Кнопка открытия списка
    private DropdownButton m_dropdown_btn;
    // Панель кнопок
    private JPanel trailing_panel;

    // Прокрутка
    private JScrollPane m_scroll_pane;
    // Панель списка
    private ItemListPanel m_list_panel;

    private int m_list_panel_height;

    public ItemListPanel getListPanel() { return m_list_panel; }
    public JScrollPane getScrollPane() { return m_scroll_pane; }

    public SelectCreateInputText(int list_panel_height) {
        own = this;
        m_list_panel_height = list_panel_height;
        buildInput();
        buildListPanel();
    }

    public void buildInput() {
        int btns_width = 16;
        m_add_btn = new AddButton(btns_width,btns_width);
        m_dropdown_btn = new DropdownButton(btns_width,btns_width);
        m_dropdown_btn.setFocusable(false);
        trailing_panel = new JPanel(new MigLayout("inset 0, gap 0 0 0 0"));

        m_add_btn.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        m_dropdown_btn.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 3));
        trailing_panel.setBackground(Color.white);

        Color disabled_background = new Color(255, 255, 255, 255);
        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, trailing_panel);
        putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));

        m_dropdown_btn.addAction(() -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    own.requestFocus();
                    if (m_scroll_pane.isVisible()) m_scroll_pane.setVisible(false);
                    else {
                        updateGUI();
                        m_scroll_pane.setVisible(true);
                    }

                }
            });
        });

        trailing_panel.add(m_add_btn, "push, grow");
        trailing_panel.add(m_dropdown_btn, "push, grow");
    }

    public void buildListPanel() {
        m_scroll_pane = new JScrollPane();
        m_list_panel = new ItemListPanel();
        m_list_panel.addItemListPanelListener(this);

        m_scroll_pane.setBackground(Color.white);
        m_scroll_pane.setVisible(false);
        m_scroll_pane.setViewportView(m_list_panel);
        m_scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for (int i = 0; i < 8; i++) {
            m_list_panel.addParameter(new TextItem("Кронштейн крепления панели переключателей","id", 28));
        }

        m_list_panel.updateUI();

//        m_scroll_pane.revalidate();
//        m_scroll_pane.repaint();

        m_list_panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("GGWP");
                m_scroll_pane.setVisible(false);
            }
        });
    }

    public void setVisibleButtons(boolean flag) {
        JComponent trailing_component = null;
        if (flag) trailing_component = trailing_panel;
        putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, trailing_component);
        if (flag == false) m_scroll_pane.setVisible(flag);
    }

    public void addDropdownAction(Runnable action) { m_dropdown_btn.addAction(action); }
    public void addAddAction(Runnable action) { m_add_btn.addAction(action); }

    @Override
    public void onPress(String text) {
        setText(text);
        setCaretPosition(0);
        m_scroll_pane.setVisible(false);
    }

    public void updateGUI() {
        m_list_panel.setWidth((int) this.getBounds().getWidth());
        m_list_panel.updateUI();
        m_scroll_pane.setMinimumSize(new Dimension((int) this.getBounds().getWidth(), m_list_panel.getHeight()));
        m_scroll_pane.setMaximumSize(new Dimension((int) this.getBounds().getWidth(), m_list_panel_height));
        m_scroll_pane.setBackground(Color.white);
    }
}
