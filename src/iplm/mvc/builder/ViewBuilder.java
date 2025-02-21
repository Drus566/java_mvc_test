package iplm.mvc.builder;

import iplm.mvc.builder.component.ViewComponent;
import iplm.mvc.views.detail.DetailsView;
import iplm.mvc.views.IView;

public class ViewBuilder {
    public IView build(ViewComponent component) {
        IView result = null;
        if (component == ViewComponent.DetailsView) {
            result = new DetailsView();
            if (component.isInit()) result.init();
        }
        else if (component == ViewComponent.DetailСontrolView) {
            result = new DetailControlView();
            if (component.isInit()) result.init();
        }
        return result;
    }
}
