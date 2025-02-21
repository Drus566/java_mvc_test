package iplm.gui.layer.intercept;

import javax.swing.*;
import java.awt.*;

@FunctionalInterface
public interface IInterceptDispatchAction {
    void run(AWTEvent e, JLayer<? extends JComponent> l);
}
