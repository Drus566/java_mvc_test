package iplm.managers;

import iplm.mvc.builder.ModelBuilder;
import iplm.mvc.builder.component.ModelComponent;
import iplm.mvc.models.IModel;

import java.util.HashMap;
import java.util.Map;

public class ModelsManager {
    private Map<ModelComponent, IModel> m_models;

    public ModelsManager() {
        m_models = new HashMap<>();
    }

    public void buildModels() {
        ModelBuilder builder = new ModelBuilder();
        for (ModelComponent model_component : ModelComponent.values()) {
            if (model_component.isActive()) m_models.put(model_component, builder.build(model_component));
        }
    }

    public IModel getModel(String model_name) {
        IModel result = null;
        for (Map.Entry<ModelComponent, IModel> entry : m_models.entrySet()) {
            ModelComponent key = entry.getKey();
            if (key.getName().equals(model_name)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }
}

