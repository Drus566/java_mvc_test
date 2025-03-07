package iplm.gui.components.detail;

import iplm.data.types.DetailParameterType;
import iplm.gui.panel.item_list_panel.items.ComboBoxInputItem;

import java.util.ArrayList;

public class DetailParameterUI extends ComboBoxInputItem {
    private ArrayList<DetailParameterType> detail_parameter_type_list;
    private DetailParameterType current_type;
    private String detail_parameter_id;

    public void setDetailParameterId(String id) { this.detail_parameter_id = detail_parameter_id; }
    public String getDetailParameterId() { return detail_parameter_id; }

    public DetailParameterType getCurrentType() { return current_type; }

    public DetailParameterUI(int width_name, ArrayList<DetailParameterType> detail_parameter_type_list) {
        super(width_name);
        this.detail_parameter_type_list = detail_parameter_type_list;

        for (DetailParameterType dpt : this.detail_parameter_type_list) {
            m_name.addItem(dpt.name);
        }
        m_name.setSelectedIndex(0);
        m_name.addItemListener(e -> setType(e.getItem().toString()));
    }

    @Override
    public void updateItem(Object detail_parameter_type_list) {
        DetailParameterType temp = null;
        if (current_type != null) temp = new DetailParameterType(current_type.id, current_type.name, current_type.type);
        this.detail_parameter_type_list = (ArrayList<DetailParameterType>) detail_parameter_type_list;
        m_name.removeAllItems();
        for (DetailParameterType dpt : this.detail_parameter_type_list) {
            m_name.addItem(dpt.name);
        }
        if (temp != null) m_name.setSelectedItem(temp.name);
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
