package controllers;

import model.Field;
import model.services.FieldService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.tableUtil.tableServices.RepresentationService;
import views.html.addField;
import views.html.fields;

public class FieldController extends Controller {

    @Transactional
    public static Result fields() {
        return ok(fields.render(RepresentationService.fillTable(Field.class, FieldService.crud().findAll())));
    }

    @Transactional
    public static Result field(Long id) {
        Field field;
        if (!id.equals(0L))
            field = FieldService.crud().findOne(id);
        else {
            field = new Field();
            field.setId(0L);
        }
        return ok(addField.render(field));
    }


    @Transactional
    public static Result saveField(Long id) {
        FieldService.saveFromRequest(id);
        return redirect("/fields");
    }

    @Transactional
    public static Result deleteField(Long id) {
        try {
            FieldService.crud().delete(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }
}
