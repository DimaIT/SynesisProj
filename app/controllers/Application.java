package controllers;

import model.Field;
import model.services.FieldService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addRecord;
import views.html.index;

import java.util.List;

public class Application extends Controller {

    @Transactional
    public static Result index(String redirect) {
        if (Boolean.valueOf(redirect))
            return ok(index.render());
        List<Field> fields = FieldService.getActualFields();
        if (fields.size() > 0)
            return ok(addRecord.render(fields));
        flash("message");
        return redirect("/fields");
    }
}
