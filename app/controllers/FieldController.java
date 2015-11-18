package controllers;

import com.google.inject.Inject;
import model.Field;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.impl.FieldServiceImpl;
import util.tableUtil.tableServices.RepresentationService;
import views.html.addField;
import views.html.fields;

/**
 * fields processing controller
 */
@With(MenuAction.class)
public class FieldController extends Controller {
    @Inject
    FieldServiceImpl fieldService;

    @Transactional
    public Result fields() {
        return ok(fields.render(RepresentationService.fillTable(Field.class, fieldService.crud.findAll())));
    }

    /**
     * add/edit record form controller
     * @param uuid of edited record or 0 overwice
     * @return field add/edit form
     */
    @Transactional
    public Result field(String uuid) {
        Field field;
        if (!uuid.equals(FieldServiceImpl.EMPTY_UUID))
            field = fieldService.crud.findOne(uuid);
        else {
            field = new Field();
            field.setUuid(null);
        }
        return ok(addField.render(field));
    }

    @Transactional
    public Result saveField(String uuid) {
        String message = fieldService.saveFromRequest(uuid);
        return message == null ? ok("redirect:/fields") : internalServerError(Messages.get("error.message") + "; " + message);
    }

    @Transactional
    public Result deleteField(String uuid) {
        try {
            fieldService.crud.delete(uuid);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }
}
