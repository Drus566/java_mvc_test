package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class DropdownButton extends ASVGButton {
    private static final String ICON_NAME = "dropdown.svg";
    private static final String TOOLTIP = "";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public DropdownButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public DropdownButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(152, 152, 152));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(224, 224, 224));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(96, 96, 96));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
