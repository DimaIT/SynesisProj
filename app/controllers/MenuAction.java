package controllers;

import com.google.inject.Inject;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import services.ResponseService;

public class MenuAction extends play.mvc.Action.Simple {
    public static final String MENU_RESPONSES = "response_count";

    @Inject
    ResponseService responseService;

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        ctx.args.put(MENU_RESPONSES, responseService.countRecords());
        return delegate.call(ctx);
    }
}

