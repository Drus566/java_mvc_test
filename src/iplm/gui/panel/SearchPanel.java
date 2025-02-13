package iplm.gui.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchPanel extends JPanel {
    public SearchPanel() {
        setLayout(new MigLayout("inset 0, gap rel -2"));
        setBorder(new FlatRoundBorder());
        setSize(300,300);
        setPreferredSize(new Dimension(0, 100));
        for (int i = 0; i < 10; i++) {
            JTextField f = new JTextField("GGWP");
            f.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    f.setBackground(Color.lightGray);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    f.setBackground(Color.white);
                }
            });
            f.setEditable(false);
            f.setBackground(Color.white);
            f.putClientProperty(FlatClientProperties.STYLE, "arc: 30");
            add(f, "width 100%, height 44!, pad 0, wrap");
        }
        setBackground(new Color(232, 232, 232, 200));
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        setBorder(new FlatLineBorder(new Insets(0,0,0,0), new Color(147, 179, 255), 1, 30));
//        putClientProperty(FlatClientProperties.STYLE, "border.arc: 30");
        setOpaque(false);
        setVisible(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                e.consume();
            }
        });
    }
}
