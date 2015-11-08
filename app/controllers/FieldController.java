package controllers;

import com.google.inject.Inject;
import model.Field;
import model.services.FieldService;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import util.tableUtil.tableServices.RepresentationService;
import views.html.addField;
import views.html.fields;

/**
 * fields processing controller
 */
@With(MenuAction.class)
public class FieldController extends Controller {
    @Inject
    FieldService fieldService;

    @Transactional
    public Result fields() {
        return ok(fields.render(RepresentationService.fillTable(Field.class, fieldService.crud.findAll())));
    }

    /**
     * add/edit record form controller
     * @param id of edited record or 0 overwice
     * @return field add/edit form
     */
    @Transactional
    public Result field(Long id) {
        Field field;
        if (!id.equals(0L))
            field = fieldService.crud.findOne(id);
        else {
            field = new Field();
            field.setId(0L);
        }
        return ok(addField.render(field));
    }

    @Transactional
    public Result saveField(Long id) {
        String message = fieldService.saveFromRequest(id);
        return message == null ? ok("redirect:/fields") : internalServerError(Messages.get("error.message") + "; " + message);
    }

    @Transactional
    public Result deleteField(Long id) {
        try {
            fieldService.crud.delete(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }
}
