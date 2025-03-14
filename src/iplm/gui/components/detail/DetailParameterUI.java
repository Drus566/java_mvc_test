package iplm.gui.components.detail;

import iplm.data.types.DetailParameter;
import iplm.data.types.DetailParameterType;
import iplm.gui.panel.item_list_panel.items.ComboBoxInputItem;

import javax.swing.*;
import java.util.ArrayList;

public class DetailParameterUI extends ComboBoxInputItem {
    private ArrayList<DetailParameterType> detail_parameter_type_list;
    private DetailParameterType current_type;
    private JTextField current_text_input;

    public DetailParameterType getCurrentType() { return current_type; }

    public DetailParameterUI(int width_name, DetailParameterType current_type, Object value, ArrayList<DetailParameterType> detail_parameter_type_list) {
        super(width_name);
        this.current_type = current_type;
        setValue(value.toString());

        this.detail_parameter_type_list = detail_parameter_type_list;

        for (DetailParameterType dpt : this.detail_parameter_type_list) {
            m_name.addItem(dpt.name);
        }
        m_name.addItemListener(e -> setType(e.getItem().toString()));

        if (detail_parameter_type_list != null && !detail_parameter_type_list.isEmpty()) {
            boolean found = false;
            for (DetailParameterType dpt : detail_parameter_type_list) {
                if (dpt.name.equalsIgnoreCase(this.current_type.name)) {
                    m_name.setSelectedItem(this.current_type.name);
                    found = true;
                    break;
                }
            }
            if (!found) {
                m_name.addItem(this.current_type.name);
                m_name.setSelectedItem(this.current_type.name);
            }
        }
    }

    public DetailParameterUI(int width_name, ArrayList<DetailParameterType> detail_parameter_type_list) {
        super(width_name);
        this.detail_parameter_type_list = detail_parameter_type_list;

        for (DetailParameterType dpt : this.detail_parameter_type_list) {
            m_name.addItem(dpt.name);
        }
//        m_name.setSelectedIndex(0);
        m_name.addItemListener(e -> setType(e.getItem().toString()));
        if (detail_parameter_type_list != null && !detail_parameter_type_list.isEmpty()) {
            current_type = detail_parameter_type_list.get(0);
        }
    }

    @Override
    public void updateItem(Object detail_parameter_type_list) {
        DetailParameterType temp = null;
        if (current_type != null) temp = new DetailParameterType(current_type.id, current_type.name, current_type.alias, current_type.type);
        this.detail_parameter_type_list = (ArrayList<DetailParameterType>) detail_parameter_type_list;
        m_name.removeAllItems();
        for (DetailParameterType dpt : this.detail_parameter_type_list) {
            m_name.addItem(dpt.name);
        }
        if (temp != null) m_name.setSelectedItem(temp.name);
    }

    @Override
    public String getPayload() {
        return getCurrentType().name;
    }

    private void setType(String type_name) {
        for (DetailParameterType dpt : detail_parameter_type_list) {
            if (dpt.name.equalsIgnoreCase(type_name)) {
                current_type = dpt;
                break;
            }
        }
    }
}
