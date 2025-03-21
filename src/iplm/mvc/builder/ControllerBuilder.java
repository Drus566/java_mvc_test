package iplm.mvc.builder;

import iplm.managers.ModelsManager;
import iplm.managers.ViewsManager;
import iplm.mvc.builder.component.ControllerComponent;
import iplm.mvc.builder.component.ModelComponent;
import iplm.mvc.builder.component.ViewComponent;
import iplm.mvc.controllers.DetailsController;
import iplm.mvc.controllers.IController;
import iplm.mvc.models.DetailModel;
import iplm.mvc.views.detail.*;

public class ControllerBuilder {
    public IController build(ControllerComponent component, ModelsManager models_manager, ViewsManager views_manager) {
        IController result = null;

        if (component == ControllerComponent.DetailsController) {
            DetailModel detail_model = null;
            DetailsView details_view = null;
            DetailControlView detail_control_view = null;
            DetailNameControlView detail_name_control_view = null;
            DetailParameterTypeControlView detail_parameter_type_control_view = null;
            DetailSettingsView detail_settings_view = null;

            detail_model = (DetailModel) models_manager.getModel(ModelComponent.DetailModel.getName());
            details_view = (DetailsView) views_manager.getView(ViewComponent.DetailsView.getName());
            detail_control_view = (DetailControlView) views_manager.getView(ViewComponent.DetailСontrolView.getName());
            detail_name_control_view = (DetailNameControlView) views_manager.getView(ViewComponent.DetailNameControlView.getName());
            detail_parameter_type_control_view = (DetailParameterTypeControlView) views_manager.getView(ViewComponent.DetailParameterTypeControlView.getName());
            detail_settings_view = (DetailSettingsView) views_manager.getView(ViewComponent.DetailSettingsView.getName());

            if (detail_model != null && details_view != null) {
                result = new DetailsController(detail_model, details_view, detail_control_view, detail_name_control_view, detail_parameter_type_control_view, detail_settings_view);
                if (component.isInit()) result.init();
            }
        }
        return result;
    }
}
