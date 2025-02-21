package iplm.gui.button;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class FilterButton extends ASVGButton {
    private static final String ICON_NAME = "filter.svg";
    private static final String TOOLTIP = "Фильтры";
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;

    public FilterButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    public FilterButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(128, 140, 145));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(159, 159, 159));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(0,0,0 ));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
