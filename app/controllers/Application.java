package controllers;

import model.Field;
import model.services.FieldService;
import model.services.ResponseService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addRecord;
import views.html.responses;

import java.util.List;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        List<Field> fields = FieldService.getActualFields();
        if (fields.size() > 0)
            return ok(addRecord.render(fields));
        return redirect("/fields");
    }

    @Transactional
    public static Result responsesController() {
        return ok(responses.render(ResponseService.table()));
    }

    @Transactional
    public static Result addResponse() {
        ResponseService.saveResponse();
        return redirect("/responses");
    }
}
