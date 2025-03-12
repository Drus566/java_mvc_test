package iplm.gui.popup;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;

public class RowListPopup extends JPopupMenu {
    private IRowListPopupListener listener;
    protected int max_visible_rows = 10;

    public RowListPopup() { this(null); }

    public RowListPopup(String label) {
        super(label);
        setLayout(new ScrollPopupMenuLayout());

        super.add(getScrollBar());
        addMouseWheelListener(event -> {
            JScrollBar scrollBar = getScrollBar();
            int amount = (event.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                    ? event.getUnitsToScroll() * scrollBar.getUnitIncrement()
                    : (event.getWheelRotation() < 0 ? -1 : 1) * scrollBar.getBlockIncrement();

            scrollBar.setValue(scrollBar.getValue() + amount);
            event.consume();
        });
    }

    public int getItemsCount() { return getComponentCount() - 1; }

    public RowListPopup(String label, IRowListPopupListener listener) {
        super(label);
        setLayout(new ScrollPopupMenuLayout());

        super.add(getScrollBar());
        addMouseWheelListener(event -> {
            JScrollBar scrollBar = getScrollBar();
            int amount = (event.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                    ? event.getUnitsToScroll() * scrollBar.getUnitIncrement()
                    : (event.getWheelRotation() < 0 ? -1 : 1) * scrollBar.getBlockIncrement();

            scrollBar.setValue(scrollBar.getValue() + amount);
            event.consume();
        });

        this.listener = listener;
    }

    public void addListener(IRowListPopupListener listener) {
        this.listener = listener;
    }

    public void updateItems(ArrayList<String> data) {
        removeItems();
        for (String d : data) {
            addItem(d);
        }
    }

    public void updateItems(HashSet<String> data) {
        removeItems();
        for (String d : data) {
            addItem(d);
        }
    }

//    public void removeItems() {
//        for (int i = 0; i < getComponentCount(); i++) {
//            remove(i);
//        }
//    }

    public void addItem(String item) {
        JMenuItem mi = new JMenuItem(item);
        mi.addActionListener(e -> {
            if (listener != null) listener.clickPopupRowAction(mi.getText());
        });
        if (item.length() > 28) mi.setToolTipText(item);
        add(mi);
    }

    public void removeItem(String item) {
        for (int i = 0; i < getComponentCount(); i++) {
            JMenuItem mi = (JMenuItem) getComponent(i);
            if (mi.getText().equals(item)) {
                remove(mi);
                return;
            }
        }
    }

    private JScrollBar popupScrollBar;
    protected JScrollBar getScrollBar() {
        if(popupScrollBar == null) {
            popupScrollBar = new JScrollBar(JScrollBar.VERTICAL);
            popupScrollBar.addAdjustmentListener(new AdjustmentListener() {
                @Override public void adjustmentValueChanged(AdjustmentEvent e) {
                    doLayout();
                    repaint();
                }
            });

            popupScrollBar.setVisible(false);
        }

        return popupScrollBar;
    }

    public int getMaximumVisibleRows() {
        return max_visible_rows;
    }

    public void setMaximumVisibleRows(int maximumVisibleRows) {
        this.max_visible_rows = maximumVisibleRows;
    }

    public void paintChildren(Graphics g){
        Insets insets = getInsets();
        g.clipRect(insets.left, insets.top, getWidth(), getHeight() - insets.top - insets.bottom);
        super.paintChildren(g);
    }

    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);

        if(max_visible_rows < getComponentCount()-1) {
            getScrollBar().setVisible(true);
        }
    }

    public void removeItems() {
        int count = getComponentCount() - 1;
        while (count > 0) {
            super.remove(1);
            --count;
        }

        if(max_visible_rows >= getComponentCount()-1) {
            getScrollBar().setVisible(false);
        }
    }

    public void remove(int index) {
        // can't remove the scrollbar
        ++index;

        super.remove(index);

        if(max_visible_rows >= getComponentCount()-1) {
            getScrollBar().setVisible(false);
        }
    }

    public void show(Component invoker, int x, int y){
        int s_width = 0, s_height = 0;

        JScrollBar scrollBar = getScrollBar();
        if (scrollBar.isVisible()) {
            int extent = 0;
            int max = 0;
            int i = 0;
            int unit = -1;
            int width = 0;
            for(Component comp : getComponents()) {
                if(!(comp instanceof JScrollBar)) {
                    Dimension preferredSize = comp.getPreferredSize();
                    width = Math.max(width, preferredSize.width);
                    if(unit < 0){
                        unit = preferredSize.height;
                    }
                    if(i++ < max_visible_rows) {
                        extent += preferredSize.height;
                    }
                    max += preferredSize.height;
                }
            }

            Insets insets = getInsets();
            int widthMargin = insets.left + insets.right;
            int heightMargin = insets.top + insets.bottom;
            scrollBar.setUnitIncrement(unit);
            scrollBar.setBlockIncrement(extent);
            scrollBar.setValues(0, heightMargin + extent, 0, heightMargin + max);

            width += scrollBar.getPreferredSize().width + widthMargin;
            int height = heightMargin + extent;

            s_width = width;
            s_height = height;

        }
        else {
            int extent = 0;
            int i = 0;

            for(Component comp : getComponents()) {
                if(!(comp instanceof JScrollBar)) {
                    Dimension preferredSize = comp.getPreferredSize();
                    s_width = Math.max(s_width, preferredSize.width);
                    if(i++ < max_visible_rows) {
                        extent += preferredSize.height;
                    }
                }
            }
            Insets insets = getInsets();
            int height_margin = insets.top + insets.bottom;
            s_height = height_margin + extent;
        }
        if (invoker != null) s_width = invoker.getWidth();

        setPopupSize(new Dimension(s_width, s_height));

        revalidate();
        repaint();
        super.show(invoker, x, y);
        if (invoker != null) invoker.requestFocus();
    }

    protected static class ScrollPopupMenuLayout implements LayoutManager {
        @Override public void addLayoutComponent(String name, Component comp) {}
        @Override public void removeLayoutComponent(Component comp) {}

        @Override public Dimension preferredLayoutSize(Container parent) {
            int visibleAmount = Integer.MAX_VALUE;
            Dimension dim = new Dimension();
            for(Component comp :parent.getComponents()){
                if(comp.isVisible()) {
                    if(comp instanceof JScrollBar){
                        JScrollBar scrollBar = (JScrollBar) comp;
                        visibleAmount = scrollBar.getVisibleAmount();
                    }
                    else {
                        Dimension pref = comp.getPreferredSize();
                        dim.width = Math.max(dim.width, pref.width);
                        dim.height += pref.height;
                    }
                }
            }

            Insets insets = parent.getInsets();
            dim.height = Math.min(dim.height + insets.top + insets.bottom, visibleAmount);

            return dim;
        }

        @Override public Dimension minimumLayoutSize(Container parent) {
            int visibleAmount = Integer.MAX_VALUE;
            Dimension dim = new Dimension();
            for(Component comp : parent.getComponents()) {
                if(comp.isVisible()){
                    if(comp instanceof JScrollBar) {
                        JScrollBar scrollBar = (JScrollBar) comp;
                        visibleAmount = scrollBar.getVisibleAmount();
                    }
                    else {
                        Dimension min = comp.getMinimumSize();
                        dim.width = Math.max(dim.width, min.width);
                        dim.height += min.height;
                    }
                }
            }

            Insets insets = parent.getInsets();
            dim.height = Math.min(dim.height + insets.top + insets.bottom, visibleAmount);

            return dim;
        }

        @Override public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();

            int width = parent.getWidth() - insets.left - insets.right;
            int height = parent.getHeight() - insets.top - insets.bottom;

            int x = insets.left;
            int y = insets.top;
            int position = 0;

            for(Component comp : parent.getComponents()) {
                if((comp instanceof JScrollBar) && comp.isVisible()) {
                    JScrollBar scrollBar = (JScrollBar) comp;
                    Dimension dim = scrollBar.getPreferredSize();
                    scrollBar.setBounds(x + width-dim.width, y, dim.width, height);
                    width -= dim.width;
                    position = scrollBar.getValue();
                }
            }

            y -= position;
            for(Component comp : parent.getComponents()) {
                if(!(comp instanceof JScrollBar) && comp.isVisible()) {
                    Dimension pref = comp.getPreferredSize();
                    comp.setBounds(x, y, width, pref.height);
                    y += pref.height;
                }
            }
        }
    }
}