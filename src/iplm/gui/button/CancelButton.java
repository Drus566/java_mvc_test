package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class CancelButton extends ASVGButton {
    private static final String ICON_NAME = "cancel.svg";
    private static final String TOOLTIP = "Отмена";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public CancelButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public CancelButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(182, 14, 14));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(229, 14, 14));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(147, 58, 58));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
