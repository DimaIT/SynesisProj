package controllers;

import com.google.inject.Inject;
import model.services.ResponseService;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

public class MenuAction extends play.mvc.Action.Simple {
    public static final String MENU_RESPONSES = "response_count";

    @Inject
    ResponseService responseService;

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        ctx.args.put(MENU_RESPONSES, responseService.countRecords());
        return delegate.call(ctx);
    }
}

