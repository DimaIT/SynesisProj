package controllers;

import com.google.inject.Inject;
import model.Field;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.CrudService;
import services.FieldService;
import services.ResponseUpdaterService;
import views.html.addRecord;
import views.html.index;

import java.util.List;

/**
 * index controller
 */
@With(MenuAction.class)
public class Application extends Controller {

    private FieldService fieldService;

    private ResponseUpdaterService responseUpdaterService;

    @Inject
    public Application(FieldService fieldService, ResponseUpdaterService responseUpdaterService) {
        this.fieldService = fieldService;
        this.responseUpdaterService = responseUpdaterService;
    }

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
        if (haveFields())
            return ok(addRecord.render(fieldService.getActualFields()));
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
        responseUpdaterService.updateAll("");
        return redirect("/");
    }

    public boolean haveFields() {
        List<Field> fields = fieldService.getActualFields();
        return fields.size() > 0;
    }
}
