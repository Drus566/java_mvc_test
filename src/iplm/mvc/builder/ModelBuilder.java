package iplm.mvc.builder;

import iplm.mvc.builder.component.ModelComponent;
import iplm.mvc.models.DetailModel;
import iplm.mvc.models.IModel;

public class ModelBuilder {
    public IModel build(ModelComponent component) {
        IModel result = null;
        if (component == ModelComponent.DetailModel) {
            result = new DetailModel();
            if (component.isInit()) result.init();
        }
        return result;
    }
}
