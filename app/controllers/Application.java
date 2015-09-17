package controllers;

import model.services.ResponseService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.responses;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @Transactional
    public static Result responsesController() {
        return ok(responses.render(ResponseService.table()));
    }
}
