package iplm.gui.panel.search_panel.components;

public enum DescriptionLineType {
    ACTUAL_LINK("Предложения:"),
    RECENT_REQUEST("Последние запросы:"),
    USED_LINK("Последние использованные:");

    private final String description;

    DescriptionLineType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
