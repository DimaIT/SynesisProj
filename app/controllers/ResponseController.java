package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import play.mvc.With;
import services.ResponseService;
import services.ResponseUpdaterService;
import views.html.responses;

/**
 * responses & web socket controller
 */
@With(MenuAction.class)
public class ResponseController extends Controller {
    @Inject
    ResponseService responseService;
    @Inject
    ResponseUpdaterService responseUpdaterService;

    @Transactional
    public Result responsesController() {
        return ok(responses.render(responseService.table()));
    }

    @Transactional
    public Result addResponse() {
        String message = responseService.saveResponse();
        return message == null ? ok("redirect:/?redirect=true") : internalServerError(message);
    }

    @Transactional
    public Result responseDelete(Long id) {
        try {
            responseService.crud.delete(id);
            responseUpdaterService.updateAll(id);
            return ok("success");
        } catch (Exception e) {
            return internalServerError("failed");
        }
    }

    /**
     * Register header responses count web socket with akka actor
     */
    public WebSocket<String> registerMenu() {
        return WebSocket.withActor(ResponseUpdaterService::menuProps);
    }

    /**
     * Register responses table web socket
     */
    public WebSocket<JsonNode> registerTable() {
        return WebSocket.withActor(ResponseUpdaterService::tableProps);
    }
}
