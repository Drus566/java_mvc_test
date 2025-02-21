package iplm.gui.layer.intercept;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.util.ArrayList;

public class InterceptLayer extends LayerUI<JComponent> {
    private static final int EMPTY_MASK = 0;

    private ArrayList<IInterceptPaintAction> paint_actions;
    private ArrayList<IInterceptDispatchAction> dispatch_actions;
    private long intercept_events;

    public InterceptLayer(long events) {
        paint_actions = new ArrayList<>();
        dispatch_actions = new ArrayList<>();
        intercept_events = events;
    }

    public InterceptLayer() {
        paint_actions = new ArrayList<>();
        dispatch_actions = new ArrayList<>();
        intercept_events = EMPTY_MASK;
    }

    public void addPaintAction(IInterceptPaintAction action) {
        paint_actions.add(action);
    }

    public void addDispatchAction(IInterceptDispatchAction action) {
        dispatch_actions.add(action);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D)g;
        if (paint_actions.isEmpty()) super.paint(g,c);
        else {
            for (IInterceptPaintAction pa : paint_actions) {
                pa.run(g2d, c);
            }
        }
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        ((JLayer<?>)c).setLayerEventMask(intercept_events);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        ((JLayer<?>)c).setLayerEventMask(EMPTY_MASK);
    }

    public void eventDispatched(AWTEvent e, JLayer<? extends JComponent> l) {
        for (IInterceptDispatchAction t : dispatch_actions) {
            t.run(e, l);
        }
    }
}
