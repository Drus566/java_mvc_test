package iplm.gui.panel.search_panel.components.types;

import iplm.gui.panel.search_panel.components.ASearchPanelLine;

import java.awt.*;

/*
    Без иконки
    Сопровождающая строка, для описания тех информационных строк, что следуют за ней
*/
public class DescriptionLine extends ASearchPanelLine {
    public enum Type {
        ACTUAL_LINK("Предложения:"),
        RECENT_REQUEST("Последние запросы:"),
        USED_LINK("Последние использованные:");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    };

    public DescriptionLine(String text) {
        ID = -1;
        setText(text);
        type = ASearchPanelLine.Type.DESCRIBE;
        setForeground(Color.gray);
    }
}
