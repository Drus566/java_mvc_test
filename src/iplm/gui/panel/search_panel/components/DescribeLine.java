package iplm.gui.panel.search_panel.components;

import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.gui.panel.search_panel.SearchPanelStrType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    Без иконки
    Сопровождающая строка, для описания тех информационных строк, что следуют за ней
*/
public class DescribeLine extends ASearchPanelStr {
    DescribeLine() {
        type = SearchPanelStrType.DESCRIBE;
        setForeground(Color.gray);
    }
}
