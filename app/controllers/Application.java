package controllers;

import model.Field;
import model.services.CrudService;
import model.services.FieldService;
import model.services.ResponseUpdaterService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addRecord;
import views.html.index;

import java.util.List;

/**
 * index controller
 */

public class Application extends Controller {

    /**
     * process '/' route
     *
     * @param redirect - true if redirect from response add
     * @return  index with success message if (redirect)
     *          response add form if fields present
     *          fields table otherwise
     */
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

    /**
     * Cleans all fields & records
     * @return redirect to index
     */
    @Transactional
    public static Result cleanDB() {
        CrudService.deleteAll();
        ResponseUpdaterService.updateAll(0L);
        return redirect("/");
    }
}
