package controllers;

import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @Transactional
    public static Result fields() {
        return TODO;
    }

    @Transactional
    public static Result field(Long id) {
        return TODO;
    }

    @Transactional
    public static Result responses() {
        return TODO;
    }

}
