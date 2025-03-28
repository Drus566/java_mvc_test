package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import java.awt.*;

public class EditButton extends ASVGButton {
    private static final String ICON_NAME = "edit.svg";
    private static final String TOOLTIP = "Редактировать";
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;

    public EditButton(String text, int width, int height) {
        init(width, height);
        build();
        buildActions();
        setText(text);
    }

    public EditButton(int width, int height) {
        init(width, height);
        build();
        buildActions();
    }

    public EditButton(String text) {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
        setText(text);
    }

    public EditButton() {
        init(WIDTH, HEIGHT);
        build();
        buildActions();
    }

    private void init(int w, int h) {
        color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 98, 182));
        hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 107, 229));
        press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(58, 79, 147));

        icon = Resources.getSVGIcon(ICON_NAME).derive(w, h);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setContentAreaFilled(false);
        setToolTipText(TOOLTIP);
    }
}
