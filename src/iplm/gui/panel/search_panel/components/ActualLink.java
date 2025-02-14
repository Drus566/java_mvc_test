package iplm.gui.panel.search_panel.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanelStrType;

import javax.swing.*;
import java.awt.*;

/*
    Иконка поиск.
    Запросы, предлагаемые первые 10 вариантов по индексу, которые соответствую введенным символам.
 */
public class ActualLink extends AInfoStr {
    private static FlatSVGIcon search_svg_icon = Resources.getSVGIcon("search.svg").derive(30,24);
    private JLabel search_icon;

    public ActualLink(String string) {
        setText(string);

        type = SearchPanelStrType.INFO;
        search_icon = new JLabel(search_svg_icon);
        search_icon.setVisible(true);

        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, search_icon);
    }
}
