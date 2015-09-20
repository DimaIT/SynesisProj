package controllers;

import model.Field;
import model.services.FieldService;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import util.tableUtil.tableServices.RepresentationService;
import views.html.addField;
import views.html.fields;

/**
 * fields processing controller
 */

public class FieldController extends Controller {

    @Transactional
    public static Result fields() {
        return ok(fields.render(RepresentationService.fillTable(Field.class, FieldService.crud.findAll())));
    }

    /**
     * add/edit record form controller
     * @param id of edited record or 0 overwice
     * @return field add/edit form
     */
    @Transactional
    public static Result field(Long id) {
        Field field;
        if (!id.equals(0L))
            field = FieldService.crud.findOne(id);
        else {
            field = new Field();
            field.setId(0L);
        }
        return ok(addField.render(field));
    }

    @Transactional
    public static Result saveField(Long id) {
        String message = FieldService.saveFromRequest(id);
        return message == null ? ok("redirect:/fields") : internalServerError(Messages.get("error.message") + "; " + message);
    }

    @Transactional
    public static Result deleteField(Long id) {
        try {
            FieldService.crud.delete(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }
}
