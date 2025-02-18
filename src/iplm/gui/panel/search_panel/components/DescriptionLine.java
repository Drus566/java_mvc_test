package iplm.gui.panel.search_panel.components;

import iplm.gui.panel.search_panel.ASearchPanelLine;
import iplm.gui.panel.search_panel.SearchPanelLineType;

import java.awt.*;

/*
    Без иконки
    Сопровождающая строка, для описания тех информационных строк, что следуют за ней
*/
public class DescriptionLine extends ASearchPanelLine {
    public DescriptionLine(String text) {
        ID = -1;
        setText(text);
        type = SearchPanelLineType.DESCRIBE;
        setForeground(Color.gray);
    }
}
