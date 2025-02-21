package iplm.gui.layer.intercept;

import javax.swing.*;
import java.awt.*;

@FunctionalInterface
public interface IInterceptPaintAction {
    void run(Graphics2D g, JComponent c);
}
