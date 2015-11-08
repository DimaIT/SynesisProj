package controllers;

import com.google.inject.Inject;
import model.Field;
import model.services.CrudService;
import model.services.FieldService;
import model.services.ResponseUpdaterService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.addRecord;
import views.html.index;

import java.util.List;

/**
 * index controller
 */
@With(MenuAction.class)
public class Application extends Controller {
    @Inject
    FieldService fieldService;
    @Inject
    ResponseUpdaterService responseUpdaterService;


    /**
     * process '/' route
     *
     * @param redirect - true if redirect from response add
     * @return  index with success message if (redirect)
     *          response add form if fields present
     *          fields table otherwise
     */
    @Transactional
    public Result index(String redirect) {
        if (Boolean.valueOf(redirect))
            return ok(index.render());
        List<Field> fields = fieldService.getActualFields();
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
    public Result cleanDB() {
        CrudService.deleteAll();
        responseUpdaterService.updateAll(0L);
        return redirect("/");
    }
}
