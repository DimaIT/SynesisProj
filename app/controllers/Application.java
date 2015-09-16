package controllers;

import model.Field;
import model.services.FieldService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.tableUtil.tableServices.RepresentationService;
import views.html.fields;
import views.html.index;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @Transactional
    public static Result fields() {
        return ok(fields.render(RepresentationService.fillTable(Field.class, FieldService.crud().findAll())));
    }

    @Transactional
    public static Result field(Long id) {
        return TODO;
    }

    @Transactional
    public static Result responses() {
        return TODO;
    }

}
