package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class DownloadButton extends ASVGButton {
    private static final String ICON_NAME = "download.svg";
    private static final String TOOLTIP = "Скачать";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public DownloadButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public DownloadButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 182, 151));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 229, 172));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(58, 147, 123));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
