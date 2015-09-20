package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.services.ResponseService;
import model.services.ResponseUpdaterService;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.responses;

/**
 * responses & web socket controller
 */

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
            ResponseUpdaterService.updateAll(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }

    /**
     * Register header responses count web socket with akka actor
     */
    public static WebSocket<String> registerMenu() {
        return WebSocket.withActor(ResponseUpdaterService::menuProps);
    }

    /**
     * Register responses table web socket
     */
    public static WebSocket<JsonNode> registerTable() {
        return WebSocket.withActor(ResponseUpdaterService::tableProps);
    }
}
