package controllers;

import model.services.ResponseService;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.responses;

public class ResponseController extends Controller {

    @Transactional
    public static Result responsesController() {
        return ok(responses.render(ResponseService.table()));
    }

    @Transactional
    public static Result addResponse() {
        boolean b = ResponseService.saveResponse();
        return b ? ok("redirect:/?redirect=true") : internalServerError(Messages.get("error.message"));
    }

    @Transactional
    public static Result responseDelete(Long id) {
        try {
            ResponseService.crud.delete(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }
}
