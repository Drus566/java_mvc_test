package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class DirectoryButton extends ASVGButton {
    private static final String ICON_NAME = "directory.svg";
    private static final String TOOLTIP = "Перейти в директорию";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public DirectoryButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public DirectoryButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(182, 160, 14));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(222, 229, 14));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(147, 140, 58));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
