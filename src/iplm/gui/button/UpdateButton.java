package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class UpdateButton extends ASVGButton {
    private static final String ICON_NAME = "update.svg";
    private static final String TOOLTIP = "Обновить";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public UpdateButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public UpdateButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(80, 80, 80));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(159, 159, 159));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(0, 0, 0));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
