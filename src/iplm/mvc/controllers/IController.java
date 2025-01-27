package iplm.mvc.controllers;

import iplm.mvc.models.IModel;
import iplm.mvc.views.IView;

public interface IController {
    void init(IModel model, IView view);
}
