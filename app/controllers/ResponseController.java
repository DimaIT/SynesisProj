package controllers;

import model.services.ResponseService;
import model.services.ResponseUpdaterService;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
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
            ResponseUpdaterService.updateAll();
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }

    @Transactional
    public static WebSocket<String> registerMenu() {
        return WebSocket.withActor(ResponseUpdaterService::props);
    }
}
